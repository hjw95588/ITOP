package com.ebchinatech.itop.api.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.itop.api.utils.CommonService;
import com.ebchinatech.itop.api.utils.HttpClientDemoUtils;
import com.ebchinatech.itop.web.punch.domain.Birth;
import com.ebchinatech.itop.web.punch.domain.PersonInfoGivelike;
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;
import com.ebchinatech.itop.web.punch.mapper.BirthMapper;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoGivelikeMapper;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoPunchMapper;
import com.ebchinatech.itop.web.punch.mapper.PunchCalendarsMapper;
import com.ebchinatech.itop.web.punch.util.SendMessageUtils;
import com.ebchinatech.kylin.common.utils.http.HttpClientUtils;
import com.ebchinatech.kylinflow.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:点赞
 */
@Slf4j
@Component("GiveLikePersonInfoData")
@Transactional(rollbackFor = Exception.class,timeout = 10000)
public class GiveLikePersonInfoData {
    @Autowired
    private CommonService commonService;

    @Autowired
    private PersonInfoGivelikeMapper personInfoGivelikeMapper;

    @Autowired
    private BirthMapper birthMapper;

    @Value("${giveLike.MAX_CLICK_VALUE}")
    private int MAX_CLICK_VALUE;//给别人点赞，最多点赞20次

    @Value("${giveLike.MAX_QUERY_PAGE}")
    private int MAX_QUERY_PAGE;//查询分页，pageNumber最大

    @Value("${giveLike.list}")
    private String listUrl;

    @Value("${giveLike.clock}")
    private String clockUrl;

    @Value("${giveLike.prev}")
    private String prevUrl;


    @Value("${giveLike.IS_SEND}")
    private String isSendMsg;


    /*** 方法描述
     * @title: giveLike
     * @createAuthor: hjw
     * @createDate: 2022/7/7 14:13
     * @description: 生日点赞
     * @param
     * @return: void
     */
    public void giveLike(){
        try {
            doGiveLike();
            log.info("===============点赞完成==================");
        } catch (Exception e) {
            log.error("#####giveLike##### e = {}", e);
            throw e;
        }
    }

    /*** 方法描述
     * @title: giveSpecialPerson
     * @createAuthor: hjw
     * @createDate: 2022/7/7 14:13
     * @description: 给特殊的人点赞
     * @param
     * @return: void
     */
    public void giveSpecialPerson(){
        try {
            doGiveSpecialPerson();
            log.info("===============点赞特殊人==================");
        } catch (Exception e) {
            log.error("#####giveSpecialPerson##### e = {}", e);
            throw e;
        }
    }

    public void doGiveSpecialPerson(){

        String nowDateStr=DateUtils.dateTimeNow("yyyyMMdd");
        String year=nowDateStr.substring(0,4);

        //1.查询好心人
        List<PersonInfoGivelike> list = personInfoGivelikeMapper.
                selectPersonInfoGivelikeList(new PersonInfoGivelike());

        //2.查询要点赞的特殊人
        List<String> peopleList=dealNeedList(year,nowDateStr);

        if(list!=null && list.size()>0){
            for(PersonInfoGivelike obj:list){
                if("0".equals(obj.getIsNeedGive())){
                    String clockUrl=this.prevUrl+this.clockUrl;//点赞的url
                    clockInfoLittle(clockUrl,obj.getSessionId(),peopleList,20);
                }
            }
        }

    }

    public List<String> dealNeedList(String year,String nowDateStr){

        List<String> peopleList=new ArrayList<>();

        //查询要点赞的特殊人
        Birth param = new Birth();
        param.setFlag("1");
        List<Birth> needList = this.birthMapper.selectBirthList(param);
        if(needList!=null && needList.size()>0)
        {
            for(Birth birth:needList)
            {
                String birthDay=year+birth.getBirDay();
                if(nowDateStr.compareTo(birthDay)<=0)
                {
                    peopleList.add(birth.getBirUserId());
                }
            }
        }
        return peopleList;

    }



    /*** 方法描述
     * @title: loadHaveGiveLike
     * @createAuthor: hjw
     * @createDate: 2022/10/14 8:58
     * @description: 记录 birth “我所点赞的人”
     * 取今天 帮助别人点赞的，第一位人的点赞部分记录
     * @param sessionId
     * @return: void
     */
    public void loadHaveGiveLike(String sessionId){
        try {
            doHaveGiveLike(sessionId);
            log.info("===============加载点赞记录完成==================");
        } catch (Exception e) {
            log.error("#####giveLike##### e = {}", e);
            throw e;
        }
    }

    public void doHaveGiveLike(String sessionId)  {
        try{
            dealGivePeron(this.prevUrl,new HashMap(),sessionId);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*** 方法描述
     * @title: nowGiveLike
     * @createAuthor: hjw
     * @createDate: 2022/10/14 9:50
     * @description: 传入sessionId，仅仅点今天的赞
     * @param sessionId
     * @param maxValue
     * @return: void
     */
    public void meGiveLike(String sessionId,Integer maxValue){
        try{
            //点赞20下
            if(maxValue==null){
                maxValue=19;
            }
            dealOneGiveBirth(sessionId,maxValue);
            log.info("===============传入sessionId，仅仅点今天的赞==================");
        }catch (Exception e){
            throw e;
        }
    }


    /*** 方法描述
     * @title: nowGiveLike
     * @createAuthor: hjw
     * @createDate: 2022/10/14 9:50
     * @description: 传入sessionId，立即进行点赞
     * @param sessionId
     * @param startDate
     * @param endDate
     * @param maxValue
     * @return: void
     */
    public void nowGiveLike(String sessionId,String startDate,String endDate,Integer maxValue){
        try{
            //点赞20下
            if(maxValue==null){
                maxValue=19;
            }
            dealNowGiveBirth(sessionId,startDate,endDate,maxValue);
            log.info("===============立即点赞完成==================");
        }catch (Exception e){
            throw e;
        }
    }

    public void doGiveLike(){
        System.setProperty("errorMsg","");
        String listUrl=this.prevUrl+this.listUrl;//获取列表url
        String clockUrl=this.prevUrl+this.clockUrl;//点赞的url

        List<PersonInfoGivelike> list = personInfoGivelikeMapper.selectPersonInfoGivelikeList(new PersonInfoGivelike());
        if(list!=null && list.size()>0){
            for(PersonInfoGivelike obj:list){
                List<String> personListInfo = getPersonListInfo(obj.getSessionId(), listUrl);
                log.info("##############sessionId={}##### 过生日peoples = {}",obj.getSessionId(),
                        JSON.toJSONString(personListInfo));
                if("1".equals(obj.getIsNeedGive())){
                    log.info("##############点赞人={}",obj.getUserName());
                    clockInfo(clockUrl,obj.getSessionId(),personListInfo);
                }
                log.info("##############givelike end##############");
            }
            isSendMessage();
        }
    }

    //获取 人员列表
    public List<String>  getPersonListInfo(String sessionId,String loginUrl){
        String errorMsg="";
        List<String> people=new ArrayList<>();//今天需要点赞的人员id集合
        Map param=new HashMap();
        param.put("deptId",0);
        param.put("pageNum",1);
        param.put("pageNum",10000);
        param.put("qryType","bir");
        try{
            String dataMsg= commonService.postForObject(sessionId, param, loginUrl);
            JSONObject object=JSON.parseObject(dataMsg);
            if(object.getString("code").equals("200")){
                JSONObject dataObject=JSON.parseObject(object.getString("data"));
                List<JSONObject> list = JSON.parseArray(dataObject.getString("list"), JSONObject.class);
                if(list!=null && list.size()>0){
                    for(JSONObject obj:list){
                        people.add(obj.getString("birUserId"));
                    }
                }

            }
            System.out.println("--------------------------------------------success--------------------------------------------");
        }catch (Exception e){
            System.out.println("--------------------------------------------error--------------------------------------------");
            log.info(e.getMessage());
            errorMsg+=sessionId+" 获取人员列表失败,抛出异常是: "+e.getMessage();
        }
        dealErrorMsg(errorMsg);
        return people;
    }

    //点赞部分
    public int clockInfoLittle(String clockUrl,String sessionId,List<String> peoples,int m){
        String errorMsg="";
        Map param=new HashMap();
        param.put("qryType","bir");
        try{
            if(peoples.size()>0){
                for(String ids:peoples){
                    param.put("id",ids);
                    dealGiveLikeLittle(clockUrl,param,sessionId,m);
                }
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorMsg+=sessionId+" 点赞人员失败,抛出异常是: "+e.getMessage();
        }
        return 1;
    }


    //点赞
    public int clockInfo(String clockUrl,String sessionId,List<String> peoples){
        String errorMsg="";
        Map param=new HashMap();
        param.put("qryType","bir");
        try{
            if(peoples.size()>0){
                for(String ids:peoples){
                    param.put("id",ids);
                    dealGiveLike(clockUrl,param,sessionId);
                }
            }


        }catch (Exception e){
            log.info(e.getMessage());
            errorMsg+=sessionId+" 点赞人员失败,抛出异常是: "+e.getMessage();
        }
        dealErrorMsg(errorMsg);
        return 1;
    }

    public void dealGiveLike(String clockUrl,Map param,String sessionId) throws InterruptedException {
        int n=0;
        for(int i=1;i<=30;i++){
            Thread.sleep(1);
            String response =commonService.postForObject(sessionId, param, clockUrl);
            JSONObject object=JSON.parseObject(response);
            if(object.getString("code").equals("200")){
                n=object.getInteger("data");
            }
            if(n>=MAX_CLICK_VALUE){
                break;
            }
        }
    }

    public void dealGiveLikeLittle(String clockUrl,Map param,String sessionId,int max) throws InterruptedException {
        int n=0;
        for(int i=1;i<=30;i++){
            Thread.sleep(1);
            String response =commonService.postForObject(sessionId, param, clockUrl);
            JSONObject object=JSON.parseObject(response);
            if(object.getString("code").equals("200")){
                n=object.getInteger("data");
            }
            if(n>=max){
                break;
            }
        }
    }


    public void dealGivePeron(String clockUrl,Map param,String defaultSessionId) throws InterruptedException {

        //获取给别人点赞的人，取第一个
        String sessionId="";

        PersonInfoGivelike info = new PersonInfoGivelike();
        info.setIsNeedGive("1");

        List<PersonInfoGivelike> list = personInfoGivelikeMapper.selectPersonInfoGivelikeList(info);
        if(list!=null && list.size()>0){
            sessionId=list.get(0).getSessionId();
        }

        if(StringUtils.isEmpty(sessionId)){
            sessionId=defaultSessionId;
        }

        List<Birth> result=new ArrayList<>();
        int n=0;
        List<String> empty=new ArrayList<>();
        for(int i=1;i<=MAX_QUERY_PAGE;i++){
            Thread.sleep(1);
            String response =commonService.postForObject(sessionId, param, clockUrl+"/userGood?pageNum="+i);
            JSONObject object=JSON.parseObject(response);
            if(object.getString("code").equals("200")){
                List<Birth> data1 = JSON.parseArray(object.getString("data"), Birth.class);
                if(data1==null || data1.size()==0){
                    break;
                }else{
                    result.addAll(data1);
                }
                System.out.println("点赞的所有人："+result.size()+"------pageNum---"+i);
            }
        }
        insertBirth(result);

    }

    public void dealOneGiveBirth(String sessionId,int m){
        String listUrl=this.prevUrl+this.listUrl;//获取列表url
        String clockUrl=this.prevUrl+this.clockUrl;//点赞的url
        //今天的生日列表
        List<String> birthList = getPersonListInfo(sessionId, listUrl);
        clockInfoLittle(clockUrl,sessionId,birthList,m);

    }

    //立刻进行点赞
    public void dealNowGiveBirth(String sessionId,String startDate,String endDate,int m){
        List<String> people=new ArrayList<>();
        Birth param = new Birth();
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        List<Birth> result = this.birthMapper.selectBirthList(param);
        if(result!=null && result.size()>0){
            for(Birth birth:result){
                people.add(birth.getBirUserId());
            }
            String clockUrl=this.prevUrl+this.clockUrl;//点赞的url
            clockInfoLittle(clockUrl,sessionId,people,m);
        }
    }

    public void insertBirth(List<Birth> result){
        if(result!=null && result.size()>0){
            for(Birth child : result){
                if(StringUtils.isNotEmpty(child.getAvatar())){
                    child.setAvatar(child.getAvatar().replaceAll("http://25.5.200.43:18080",
                            "https://ird-new.ebchina.com:18090"));
                }
            }
            this.birthMapper.insertBirthBatch(result);
        }
    }


    public void isSendMessage(){
        if(StringUtils.isNotEmpty(System.getProperty("errorMsg")) && "true".equals(isSendMsg)){
            //发送短信
            SendMessageUtils.sendMessage(System.getProperty("errorMsg"),"点赞失败的说明");
        }
    }

    public void dealErrorMsg(String errorMsg){
        if(StringUtils.isNotEmpty(errorMsg)){
            String msg = System.getProperty("errorMsg")+"\r\n"+errorMsg;
            System.setProperty("errorMsg",msg);
        }
    }




}
