package com.ebchinatech.itop.api.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.itop.api.utils.CommonService;
import com.ebchinatech.itop.api.utils.HttpClientDemoUtils;
import com.ebchinatech.itop.web.punch.domain.PersonInfoPunch;
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;
import com.ebchinatech.itop.web.punch.domain.SysEmpInfo;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoPunchMapper;
import com.ebchinatech.itop.web.punch.mapper.PunchCalendarsMapper;
import com.ebchinatech.itop.web.punch.mapper.SysEmpInfoMapper;
import com.ebchinatech.itop.web.punch.util.SendMessageUtils;
import com.ebchinatech.kylin.common.utils.http.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/*** 方法描述
 * @title:
 * @createAuthor: hjw
 * @createDate: 2022/9/11 11:30
 * @description: 同步HR人力人员信息
 *https://hr-saas.ebchinatech.com/hr_saas/mobileV16/mobLogin 登录接口
 * https://hr-saas.ebchinatech.com/hr_saas/mobileV18/getEmpList?resId=10&empName= （form-data count:10000） 查询工号
 *
 *
 * https://hr-saas.ebchinatech.com/hr_saas/mobile/mobileV18Employee/findEmployeeAllinfo?pkempid=95988&infoType=all
 *   （header token_id） 获取基本信息，包含岗级（employinfo（岗位信息）-》cascadingJobGrade）
 *https://hr-saas.ebchinatech.com/hr_saas/mobileV16Employee/findEmployeeAllinfo?pkempid=94802&infoType=all
 *   （header token_id） 获取基本信息，employinfo （不含岗级，没有工号）
 * @param null
 * @return:
 */
@Slf4j
@Component("SyncHRPersonInfoData")
@Transactional(rollbackFor = Exception.class,timeout = 10000)
public class SyncHRPersonInfoData {

    @Autowired
    private PersonInfoPunchMapper personInfoPunchMapper;

    @Autowired
    private SysEmpInfoMapper sysEmpInfoMapper;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private CommonService commonService;


    @Value("${PUNCH.PREV}")
    private String prevUrl;

    @Value("${PUNCH.LOGIN}")
    private String loginUrl;

    @Value("${PUNCH.EMP_LIST}")
    private String empListUrl;

    @Value("${PUNCH.INFO1}")
    private String info1Url;//无职级，工号

    @Value("${PUNCH.INFO2}")
    private String info2Url;//有职级，工号

    @Value("${PUNCH.TEST}")
    private String testUrl;

    @Value("${PUNCH.FILE_PATH}")
    private String filePath;

    @Value("${PUNCH.COOKIE}")
    private String cookieUrl;


    /*** 方法描述
     * @title: syncPersonPhoto
     * @createAuthor: hjw
     * @createDate: 2022/9/23 22:36
     * @description:同步人员头像
     * @param
     * @return: void
     */
    public void syncPersonPhoto(){
        try{
            doPersonPhoto();
        }catch(Exception e){
            log.error("#####syncPersonPhoto##### e = {}", e);
            throw e;
        }
    }


    public void doPersonPhoto(){

        File file = new File(this.filePath);
        if(!file.exists()){
            file.mkdirs();
        }

        List<SysEmpInfo> list =
                this.sysEmpInfoMapper.selectSysEmpInfoList(new SysEmpInfo());
        if(list!=null && list.size()>0){
            list=list.stream().filter(item->StringUtils.isNotEmpty(item.getPhotoUrl()))
                    .collect(Collectors.toList());
            String sessionId = getSessionId();
            log.info("============sessionId=========={}",sessionId);
            if(StringUtils.isEmpty(sessionId)){
                return;
            }
            int n=0;
            for(SysEmpInfo info:list){
                dealSleepTime(1);
                String[] split = info.getPhotoUrl().split("/");
                String fileName=info.getEmpName()+"_"+info.getWorkno()+"_"+info.getEmpid()+"_"+split[split.length-1];
                System.out.println((++n)+"-------------"+fileName);
                if(n%20==0){
                    sessionId = getSessionId();
                }
                if("undefined".equals(split[split.length-1])){
                    continue;
                }
                if(!new File(this.filePath+fileName).exists()){
                    //不存在，则创建
                    saveFile(info,fileName,sessionId);
                }
            }
        }
    }

    //处理停顿时间
    public void dealSleepTime(int n)  {
            try{
                Thread.sleep(n);
            }catch (Exception e){
                e.printStackTrace();
            }
    }


    public String  getSessionId(){
        return commonService.getSessionId(this.prevUrl + this.cookieUrl);
    }

    public void saveFile(SysEmpInfo info, String fileName,String sessionId){
        ResponseEntity<byte[]> forEntity = commonService.getForEntitySetSession(this.prevUrl+"/" + info.getPhotoUrl(),sessionId);
        FileOutputStream fileOutputStream=null;
        try {
            if(forEntity!=null){
                fileOutputStream = new FileOutputStream(this.filePath+fileName);
                fileOutputStream.write(forEntity.getBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
            }catch (Exception e){

            }
        }
    }

    /*** 方法描述
     * @title: punchByOnePhone
     * @createAuthor: hjw
     * @createDate: 2022/9/11 11:49
     * @description: 同步人员信息
     * @param phone
     * @return: void
     */
    public void syncHRPersonInfo(String empName){
        try {
            syncPersonInfo(empName);
        } catch (Exception e) {
            log.error("#####SyncData##### e = {}", e);
            throw e;
        }
    }

    //通过登录获取 token_id
    public void getTokenId(){
        String loginUrl=this.prevUrl+this.loginUrl;
        List<Map> list = this.personInfoPunchMapper.queryAllowPunchPerson(null);
        if(list!=null && list.size()>0){
            Map map = list.get(0);
            String tokenId=loginInfo(map,loginUrl);
            if(StringUtils.isNotEmpty(tokenId)){
                System.setProperty("token_id",tokenId);
            }
        }
    }

    public void syncPersonInfo(String empName) {

        System.setProperty("token_id","2c4b2a8a-c041-493d-a677-66a2e698db65");

        if(this.valTokenId()){
            //不执行登录请求
        }else{
            getTokenId();
        }
        List<Map> empList = getEmpList();
        empList=empList.stream().filter(item->{

            return item.get("empName")!=null && item.get("empName").toString().contains(empName) ?true:false;
        }).collect(Collectors.toList());

        //升序
       Collections.sort(empList, Comparator.comparing(item->Integer.valueOf(item.get("empid")+"")));
        /*empList= empList.stream().sorted(Comparator.comparing(item->Integer.parseInt(item.get("empid")+""))
        ).collect(Collectors.toList());*/

        /*int n=0;
        for(Map ma:empList){
            n++;
           System.out.println(ma.get("workno")+"-------"+ma.get("empName")+"--------"+ma.get("empid")+"---"+n);

        }*/

        List<Map> sysEmpInfoList=new ArrayList();
        int m=0;
        if(empList!=null && empList.size()>0){
            for(Map ma:empList){
                sleepTimeTest(1);
                System.out.println(ma.get("workno")+"-------"+ma.get("empName")+"--------"+ma.get("empid")+"---"+(++m));
                sysEmpInfoList.add(dealInfo02(ma));
            }
        }

       if(sysEmpInfoList!=null && sysEmpInfoList.size()>0){
           for(Map ma:sysEmpInfoList){
               List<Map> demoList=new ArrayList();
               demoList.add(ma);
               sysEmpInfoMapper.insertEmpInfoBatch(demoList);
           }
       }
        System.out.println("-------------结束了---------------");

    }

    public void sleepTimeTest(int a) {
        try{
            Thread.sleep(a);
        }catch (Exception e){

        }
    }

    public Map dealInfo02(Map map){
        String url=this.prevUrl+this.info2Url+"?infoType=all&pkempid="+map.get("empid");
        Map result=new HashMap();
        try{
            String dataMsg= commonService.postForObjectFormDataParam(new HashMap(), url);
            JSONObject object=JSON.parseObject(dataMsg);
            if(object.getInteger("status")==1){
                List<Map> other1List = JSON.parseArray(object.getString("data"), Map.class);
                dealEmpInfo(other1List,result);
                dealBaseInfo(other1List,result);
                result.put("other2",dataMsg);
                result.put("photoUrl",map.get("photoUrl"));
                dealInfo01(map,result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public Map dealEmpInfo(List<Map> other1List,Map result){

        Map employinfoMap=other1List.stream().filter(item->{
            return item.get("code").toString().equals("employinfo");
        }).collect(Collectors.toList()).get(0);

        List<Map> employinfoList=(List<Map>) employinfoMap.get("data");

        for(Map ma:employinfoList){
            if("empid".equals(ma.get("fieldCode")+"")){
                result.put("empid",ma.get("fieldValue"));
            }else{
                result.put(ma.get("fieldCode")+"",ma.get("fieldText"));
            }
        }
        return result;

    }

    public Map dealBaseInfo(List<Map> other1List,Map result){
        Map employinfoMap=other1List.stream().filter(item->{
            return item.get("code").toString().equals("baseinfo");
        }).collect(Collectors.toList()).get(0);

        List<Map> employinfoList=(List<Map>) employinfoMap.get("data");

        for(Map ma:employinfoList){
            if("empid".equals(ma.get("fieldCode")+"")){
                result.put("empid",ma.get("fieldValue"));
            }else{
                result.put(ma.get("fieldCode")+"",ma.get("fieldText"));
            }
        }
        return result;

    }

    public void dealInfo01(Map param,Map resultMap){
        String url=this.prevUrl+this.info1Url+"?infoType=all&pkempid="+param.get("empid");
        try{
            String dataMsg= commonService.postForObjectFormDataParam(new HashMap(), url);
            JSONObject object=JSON.parseObject(dataMsg);
            if(object.getInteger("status")==1){
                resultMap.put("other1",dataMsg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }




    //登录
   public String  loginInfo(Map ma,String loginUrl){
        String tokenId="";
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
               }else{
               tokenId=object.getString("token_id");
               log.info(ma.get("userName").toString()+"-------------登录成功-------------phone = {}", ma.get("phone").toString());
           }
       }catch(Exception e){
           log.info(ma.get("userName").toString()+"##############登录失败##########抛出异常是={}##### phone = {}",e.getMessage(),ma.get("phone").toString());
           }
      return tokenId;
   }

    //校验tokenId是否有效
    public boolean  valTokenId(){
        String url=this.prevUrl+this.testUrl;
        if(StringUtils.isEmpty(System.getProperty("token_id"))){
            return false;
        }
        String tokenId=System.getProperty("token_id");
        try{
            String response = HttpClientDemoUtils.doPostParamToBody(url,new HashMap(),tokenId);
            JSONObject object=JSON.parseObject(response);
            if(object.getInteger("status")==1){
                //有效
                return true;
            }
        }catch(Exception e){
            log.info("-校验失败-");
        }
        return false;
    }


   //获得 empList
   public List<Map> getEmpList(){
      List<Map> result=new ArrayList();
       String tokenId=System.getProperty("token_id");
       log.info("tokenId  ={}",tokenId);
       String url=this.prevUrl+this.empListUrl;
       Map param=new HashMap();
       param.put("count",5000+"");
       try{
           String dataMsg= commonService.postForObjectFormDataParam(param, url);
           JSONObject object=JSON.parseObject(dataMsg);
           result=JSON.parseArray(object.getString("rows"),Map.class);
       }catch(Exception e){
            e.printStackTrace();
           }
       return result;
   }

}
