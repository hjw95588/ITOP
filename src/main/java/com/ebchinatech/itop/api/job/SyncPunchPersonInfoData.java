package com.ebchinatech.itop.api.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.itop.api.utils.HttpClientDemoUtils;
import com.ebchinatech.itop.common.utils.DHClientUtils;
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoPunchMapper;
import com.ebchinatech.itop.web.punch.mapper.PunchCalendarsMapper;
import com.ebchinatech.itop.web.punch.util.SendMessageUtils;
import com.ebchinatech.kylin.common.utils.http.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:获取人员信息，进行登录打卡
 */
@Slf4j
@Component("SyncPunchPersonInfoData")
@Transactional(rollbackFor = Exception.class,timeout = 10000)
public class SyncPunchPersonInfoData {

    @Autowired
    private PersonInfoPunchMapper personInfoPunchMapper;

    @Autowired
    private PunchCalendarsMapper punchCalendarsMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${PUNCH.LOGIN}")
    private String loginUrl;

    @Value("${PUNCH.CLOCK}")
    private String clockUrl;

    @Value("${PUNCH.PREV}")
    private String prevUrl;

    @Value("${PUNCH.IS_SEND}")
    private String isSendMsg;


    /*** 方法描述
     * @title: punchSelectPerson
     * @createAuthor: hjw
     * @createDate: 2022/6/21 12:08
     * @description:
     * @param phone 只能传一个手机号
     * @return: void
     */
    public void punchByOnePhone(String phone){
        try {
            punchOnePerson(phone);
        } catch (Exception e) {
            log.error("#####SyncData##### e = {}", e);
            throw e;
        }
    }

    /**
     *
     *打卡所有人
     * @return
     */
    public void syncPunch() {
        try {
            syncPunchAll();
        } catch (Exception e) {
            log.error("#####SyncData##### e = {}", e);
            throw e;
        }
    }

    //是否 为工作日 true工作日 false非工作日
    public boolean isWorkDay(){
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
        PunchCalendars calendars=this.punchCalendarsMapper.selectPunchCalendarsById(yyyyMMdd);
        if(calendars!=null && "1".equals(calendars.getDateType()))
        {
            return true;
        }
        return false;
    }

    /**
     * 查询需要打卡的人员
     */
    public void syncPunchAll() {
        if(isWorkDay()){
            //今天是正常工作日
            doPunchAllAllowPeron();
        }else{
            log.info("今天 {}  不是工作日，不需要打卡",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        }
    }

    public void punchOnePerson(String phone) {
        if(isWorkDay()){
            //今天是正常工作日
            doPunchOneAllowPeron(phone);
        }else{
            log.info("今天 {}  不是工作日，不需要打卡",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        }
    }

    //一个人 打卡
    public void doPunchOneAllowPeron(String phone){
        if(StringUtils.isNotEmpty(phone)){
            //这种写法，多线程下应该会有问题
            System.setProperty("errorMsg","");
            String loginUrl=this.prevUrl+this.loginUrl;
            String clockUrl=this.prevUrl+this.clockUrl;
            List<Map> list = this.personInfoPunchMapper.queryAllowPunchPerson(phone);
            if(list!=null && list.size()>0){
                Map ma=list.get(0);
                dealSleepTime(ma);
                String tokenId=loginInfo(ma,loginUrl);
                if(StringUtils.isNotEmpty(tokenId)){
                    clockInfo(ma,clockUrl,tokenId);
                }
            }
            isSendMessage();
        }
    }

    //处理停顿时间
    public void dealSleepTime(Map ma)  {
        if(ma.get("sleepTime")!=null && StringUtils.isNotEmpty(ma.get("sleepTime").toString())){
            //需要停顿
            try{
                //随机一个时间
                int maxTime=Integer.parseInt(ma.get("sleepTime").toString());
                int sleepTime= new Random().nextInt(maxTime-1+1)+1;
                log.info("停顿时间："+sleepTime+" 秒");
                Thread.sleep(sleepTime*1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //打卡所有 满足条件的人员
    public void doPunchAllAllowPeron(){
        System.setProperty("errorMsg","");
        String loginUrl=this.prevUrl+this.loginUrl;
        String clockUrl=this.prevUrl+this.clockUrl;

        List<Map> list = this.personInfoPunchMapper.queryAllowPunchPerson(null);
        if(list!=null && list.size()>0){
            for(Map ma:list){
                dealSleepTime(ma);
                String tokenId=loginInfo(ma,loginUrl);
                if(StringUtils.isNotEmpty(tokenId)){
                    clockInfo(ma,clockUrl,tokenId);
                }
            }
        }
        isSendMessage();
    }

    public void isSendMessage(){
        if(StringUtils.isNotEmpty(System.getProperty("errorMsg")) && "true".equals(isSendMsg)){
            //发送短信
            SendMessageUtils.sendMessage(System.getProperty("errorMsg"),"打卡失败的说明");
        }
    }



    //登录
   public String  loginInfo(Map ma,String loginUrl){
        String tokenId="",errorMsg="";
       Map param=new HashMap();
       param.put("account", ma.get("phone").toString());
       param.put("password", ma.get("password").toString());
       try{
           String response = HttpClientUtils.doPostParamToBody(loginUrl,param);
           JSONObject object=JSON.parseObject(response);
           if(object.getInteger("status")!=1){
               //登录失败
               log.info(ma.get("userName").toString()+"##############登录失败##########原因是={}##### phone = {}",object.getString("message"),
                       ma.get("phone").toString());
               errorMsg+=ma.get("userName").toString()+"  "+ma.get("phone").toString()+" 登录失败,原因是: "+object.getString("message");
           }else{
               tokenId=object.getString("token_id");
               log.info(ma.get("userName").toString()+"-------------登录成功-------------phone = {}", ma.get("phone").toString());
           }
       }catch(Exception e){
           log.info(ma.get("userName").toString()+"##############登录失败##########抛出异常是={}##### phone = {}",e.getMessage(),ma.get("phone").toString());
           errorMsg+=ma.get("userName").toString()+"  "+ma.get("phone").toString()+" 登录失败,抛出异常是: "+e.getMessage();
       }
       dealErrorMsg(errorMsg);
      return tokenId;
   }

   public void dealErrorMsg(String errorMsg){
        if(StringUtils.isNotEmpty(errorMsg)){
            String msg = System.getProperty("errorMsg")+"\r\n"+errorMsg;
            System.setProperty("errorMsg",msg);
        }
   }

   //打卡
   public int clockInfo(Map ma, String clockUrl,String tokenId){
        String errorMsg="";
      int n=0;
       clockUrl+="&regAddr=" +ma.get("addr").toString()+"&lng="+ma.get("longitude").toString()+"&lat="+ma.get("latitude").toString();
       Map param=new HashMap();
       try{
           String response = HttpClientDemoUtils.doPostParamToBody(clockUrl,param,tokenId);
           JSONObject object=JSON.parseObject(response);
           if(object.getInteger("status")!=1){
               //打卡失败--数据接口调用失败

               log.info(ma.get("userName").toString()+"##############打卡失败##########原因是={}##### phone = {}","打卡接口调用失败",
                       ma.get("phone").toString());
               errorMsg+=ma.get("userName").toString()+"  "+ma.get("phone").toString()+" 打卡失败,原因是: 打卡接口调用失败";
           }else{
               n=1;

               String msg="";
               JSONObject errorObject=JSON.parseObject(object.getString("data"));
               if(errorObject.getBoolean("isError")){
                   List<String> list = JSON.parseArray(errorObject.getString("reason"), String.class);
                   if(list!=null && list.size()>0){
                       msg=StringUtils.join(list,",");
                   }
                   log.info(ma.get("userName").toString()+"##############打卡失败##########原因是={}##### phone = {}",msg,
                           ma.get("phone").toString());
                   errorMsg+=ma.get("userName").toString()+"  "+ma.get("phone").toString()+" 打卡失败,原因是: "+msg;
               }else{
                   log.info(ma.get("userName").toString()+"-------------打卡成功-------------phone = {}", ma.get("phone").toString());
               }

           }
       }catch(Exception e){
           log.info(ma.get("userName").toString()+"##############打卡失败##########抛出异常是={}##### phone = {}",e.getMessage(),
                   ma.get("phone").toString());
           errorMsg+=ma.get("userName").toString()+"  "+ma.get("phone").toString()+" 打卡失败,抛出异常是: "+e.getMessage();
       }
       dealErrorMsg(errorMsg);
       return n;
   }

}
