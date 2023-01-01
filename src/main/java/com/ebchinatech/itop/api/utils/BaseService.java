package com.ebchinatech.itop.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import org.springframework.util.LinkedMultiValueMap;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
  * 公共的RestTemplate封装
  * @author hjw
  * @version 1.0
  * @date 2022年7月7日16:45:31
  * Copyright
  */
public class BaseService  {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 设置http头信息，应用于rest风格的post请求
     * @return
     */
    public HttpHeaders settingHead(){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("cookie", "SESSION="+System.getProperty("sessionId"));
        return headers;
    }

    public HttpHeaders settingHead2(){

        // 请求头设置,x-www-form-urlencoded格式的数据 new MediaType("application", "x-www-form-urlencoded")
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("token_id", System.getProperty("token_id"));
        return headers;
    }

    /*** 方法描述
     * @title: postForObjectFormDataParam
     * @createAuthor: hjw
     * @createDate: 2022/9/11 17:49
     * @description: formData 参数
     * @param data
     * @param url
     * @return: java.lang.String
     */
    public String postForObjectFormDataParam(Map data, Object... url) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String,String>();
        if(data!=null){
            for(Object object:data.keySet()){
                map.add(object+"",data.get(object)+"" );
            }
        }
        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<MultiValueMap<String, String>>(map, settingHead2());

        // 发送post请求，并打印结果，以String类型接收响应结果JSON字符串
        String result = restTemplate.postForObject(getUrl(url), request, String.class);
        return result;
    }

    /*** 方法描述
     * @title: postForObject
     * @createAuthor: hjw
     * @createDate: 2022/7/7 18:16
     * @description:
     * @param data 数据
     * @param url 请求地址
     * @return: java.lang.String
     */
    public String postForObject(String sessionId,Object data,Object... url) {
        // TODO Auto-generated method stub
        System.setProperty("sessionId",sessionId);
        HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(data),settingHead());
        String message = restTemplate.postForObject(getUrl(url), formEntity, String.class);
        return message;
    }

    /**
     *
     * get请求 ，封装
     * @param params 路径顺序传参 serviceUrl,methedUrl,params[0],params[1]
     *          例:companyService.getForObject(companyServiceUrl,"company/queryInfo",companyId);
     * @return
     * @author hjw
     * @date 2022年7月7日16:45:54
     */
    public String getForObject(Object... params) {
        String message = restTemplate.getForObject(getUrl(params),String.class);
        return message;
    }

    public ResponseEntity<byte[]> getForEntitySetSession(String url2,String session) {
        //第一个请求
        ResponseEntity<byte[]> result3=null;
        try{
            result3 = restTemplate.exchange(url2, HttpMethod.GET, settingHead3(session), byte[].class);
        }catch(Exception e){
            e.printStackTrace();
        }

        return result3;
    }

    public String getSessionId(String url1){
        //第一个请求
        String session="";
        ResponseEntity<byte[]> result3=null;
        ResponseEntity forEntity = restTemplate.getForEntity(url1, String.class, new HashMap());
        HttpHeaders headers = forEntity.getHeaders();
        List<String> strings = headers.get("Set-Cookie");

        if(strings!=null && strings.size()>0){
            strings=strings.stream().filter(item->{
                return  item.contains("SESSION");
            }).collect(Collectors.toList());
            if(strings!=null && strings.size()>0){
                session=strings.get(0);
            }
        }
        return session;
    }

    public ResponseEntity<byte[]> getForEntity(String url1,String url2) {
        //第一个请求
        String session="";
        ResponseEntity<byte[]> result3=null;
        ResponseEntity forEntity = restTemplate.getForEntity(url1, String.class, new HashMap());
        HttpHeaders headers = forEntity.getHeaders();
        List<String> strings = headers.get("Set-Cookie");

        if(strings!=null && strings.size()>0){
            strings=strings.stream().filter(item->{
                return  item.contains("SESSION");
            }).collect(Collectors.toList());
            if(strings!=null && strings.size()>0){
                session=strings.get(0);
            }
        }
        try{
            result3 = restTemplate.exchange(url2, HttpMethod.GET, settingHead3(session), byte[].class);
        }catch(Exception e){
           e.printStackTrace();
        }

        return result3;
    }

    public HttpEntity<MultiValueMap<String, Object>> settingHead3(String session){
        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie", session);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
        return formEntity;
    }



    private String getUrl(Object... params) {
        String url="";
        if(params!=null&&params.length>0){

            for(Object o: params){
                url=url+o+"/";
            }
            url=url.substring(0,url.length()-1);
        }
        return url;
    }

}
