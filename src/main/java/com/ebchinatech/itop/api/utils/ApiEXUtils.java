package com.ebchinatech.itop.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.kylin.common.utils.http.HttpClientUtils;
import com.ebchinatech.kylin.framework.redis.RedisCache;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ApiEXUtils {

    /**
     * get access_token url
     */
    @Value("${ex.token.url}")
    private  String getWXTokenUrl;
    /**
    * 应用Id
     **/
    @Value("${ex.agentId}")
    private  String agentid;

    /**
     * 企业CorpId
     **/
    @Value("${ex.corpId}")
    private  String corpId;

    /**
     * 应用的凭证密钥
     */
    @Value("${ex.corpSecret}")
    private  String corpSecret;

    @Value("${ex.sendMessage}")
    private  String getSendMsgUrl;

    @Value("${ex.exinticket.url}")
    private  String getTicketUrl;

    @Autowired
    private  RedisCache redisCache;




    /**
     * 获取光大E信中的接口数据
     * @param apiUrl
     * @return
     * @throws Exception
     */
    public  JSONObject getApiData(String apiUrl) throws Exception{
        String result = HttpClientUtils.doPostParamToUrl(apiUrl,null);
        if(StringUtils.isNotBlank(result)){
            //解析接口返回数据
            JSONObject resultInfo = JSONObject.parseObject(result);
            if(resultInfo.containsKey("errcode") && resultInfo.getInteger("errcode") == 0){
                return resultInfo;
            }
        }
        return null;
    }

    /**
     * 获取光大E信的 token接口
     * @return
     * @throws Exception
     */
    public  String getToken() throws Exception{
        String token = redisCache.getCacheObject("access_token");
        if(StringUtils.isBlank(token)){
            //表示未生成token
            String tokenUrl = String.format(getWXTokenUrl,corpId,corpSecret);
            JSONObject tokenInfo = getApiData(tokenUrl);
            if(tokenInfo != null){
                token = tokenInfo.getString("access_token");
                redisCache.setCacheObject("access_token",token,5, TimeUnit.MINUTES);
            }else{
                System.out.println("获取WX令牌 接口出现错误");
            }
        }
        return token;
    }

    /**
     * 获取临时票据
     * @return
     * @throws Exception
     */
    public  String  getTicket() throws Exception{
        String token = getToken();
        String ticket = redisCache.getCacheObject("_JSTICKET");
        if(StringUtils.isBlank(ticket) && StringUtils.isNotBlank(token)){
            //临时票据
            String jsapi_ticket = String.format(getTicketUrl,token);
            System.out.println("最新获取签名的URL:" + jsapi_ticket);
            JSONObject ticketInfo = getApiData(jsapi_ticket);
            if(ticketInfo != null){
                ticket = ticketInfo.getString("ticket");
                redisCache.setCacheObject("_JSTICKET",ticket,5, TimeUnit.MINUTES);
            }else{
                System.out.println("获取WX 签名接口出现错误");
            }
        }

        return ticket;
    }

    /**
     * 时间戳工具
     * @param isUnix
     * @return
     */
    public  Long getCurrentTime(Boolean isUnix) {
        if (isUnix == null) {
            isUnix = true;
        }
        return isUnix ? System.currentTimeMillis() / 1000L : System.currentTimeMillis();
    }


    public  String send(String toUser,String content){
        try {

            String token = getToken();
            if(StringUtils.isBlank(token)){
                return "获取access_tlken失败！！";
            }
            String sendMsgUrl = String.format(getSendMsgUrl,token);

            JSONObject reqJsonObject = new JSONObject();
            reqJsonObject.put("touser", toUser);
            reqJsonObject.put("msgtype", "text");
            reqJsonObject.put("agentid", Integer.parseInt(agentid));

            JSONObject contentJson = new JSONObject();
            contentJson.put("content", content);

            reqJsonObject.put("text", contentJson);

            JSONObject msgObj = sendHttpPost(sendMsgUrl, null, reqJsonObject);
            if (msgObj.get("errcode") != null && msgObj.getInteger("errcode") == 0) {
                //推送成功
                return "推送成功";
            } else {
                String errorMsg = String.valueOf(msgObj.get("errmsg"));
                return errorMsg;
            }
        }catch (Exception e){
            System.out.println("推送消息到企业微信失败，msg:"+e.getMessage());
        }
        return "";
    }

    public  JSONObject sendHttpPost(String url, Map<String, String> headers, JSONObject sendDataJson) throws Exception {
        String content = doPost(url, headers, sendDataJson);
        if (StringUtils.isNotBlank(content)) {
            JSONObject jsonb = JSONObject.parseObject(content);
            return jsonb;
        }
        return null;
    }

    public  String doPost(String url, Map<String, String> headers, JSONObject sendDataJson) {
        HttpPost post = new HttpPost(url);
        //构造消息头
        if (headers != null && headers.size() > 0) {
            for (String name : headers.keySet()) {
                String value = headers.get(name);
                post.setHeader(name, value);
            }
        }
        //构建消息实体
        String sendData = "";
        if (sendDataJson != null) {
            sendData = sendDataJson.toString();
        }
        StringEntity stringEntity = new StringEntity(sendData, "UTF-8");
        stringEntity.setContentType("application/json");
        post.setEntity(stringEntity);
        //发送后请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity httpEntity = null;
        try {
            response = httpClient.execute(post);
            //检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                httpEntity = response.getEntity();//获取响应实体
                return EntityUtils.toString(httpEntity, "utf-8");
            } else {
                //请求报错
                return  "请求地址出错";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpEntity != null) {
                    EntityUtils.consume(httpEntity);//消耗响应实体，并关闭相关资源占用
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
 
}
