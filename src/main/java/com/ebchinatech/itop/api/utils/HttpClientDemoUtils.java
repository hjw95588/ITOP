package com.ebchinatech.itop.api.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

public class HttpClientDemoUtils {
 private static final Logger log = LoggerFactory.getLogger(HttpClientDemoUtils.class);

 public HttpClientDemoUtils() {
 }

 private static RequestConfig requestConfig = RequestConfig.custom()
         .setSocketTimeout(45000)
         .setConnectTimeout(45000)
         .setConnectionRequestTimeout(45000)
         .build();

 public static String doPostParamToUrl(String url, String data) {
     CloseableHttpClient httpClient = HttpClients.createDefault();
     HttpPost httpPost = new HttpPost(url);
     httpPost.setConfig(requestConfig);
     String context = "";
     if (!StringUtils.isEmpty(data)) {
         StringEntity body = new StringEntity(data, "utf-8");
         httpPost.setEntity(body);
     }

     httpPost.addHeader("Content-Type", "application/json");
     CloseableHttpResponse response = null;

     try {
         response = httpClient.execute(httpPost);
         HttpEntity entity = response.getEntity();
         context = EntityUtils.toString(entity, "UTF-8");
     } catch (Exception var15) {
         var15.getStackTrace();
     } finally {
         try {
             if (response != null) {
                 response.close();
             }

             httpPost.abort();
             httpClient.close();
         } catch (Exception var14) {
             var14.getStackTrace();
         }

     }

     return context;
 }

    public static String doPostParamNeedHeaders(String url, String data) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        String context = "";
        if (!StringUtils.isEmpty(data)) {
            StringEntity body = new StringEntity(data, "utf-8");
            httpPost.setEntity(body);
        }

        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            context = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception var15) {
            var15.getStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                httpPost.abort();
                httpClient.close();
            } catch (Exception var14) {
                var14.getStackTrace();
            }

        }

        return context;
    }


 public static String doPostParamToBody(String url, Map<String, String> mapData) {
     CloseableHttpResponse response = null;
     CloseableHttpClient httpClient = HttpClients.createDefault();
     HttpPost httpPost = new HttpPost(url);
     httpPost.setConfig(requestConfig);
     try {
         httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
         List<NameValuePair> nameValuePairs = new ArrayList();
         if (!CollectionUtils.isEmpty(mapData)) {
             Set keySet = mapData.keySet();
             Iterator it = keySet.iterator();

             while(it.hasNext()) {
                 String k = (String)it.next();
                 String v = (String)mapData.get(k);
                 nameValuePairs.add(new BasicNameValuePair(k, v));
             }

             httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         }
         HttpEntity entity = response.getEntity();
         if (entity != null) {
             return EntityUtils.toString(entity, "UTF-8");
         }
     } catch (Exception var10) {
         var10.printStackTrace();
     }

     return null;
 }

    public static String doPostParamToBodyNeedSessionId(String url, Map<String, String> mapData,String sessionId) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {

            httpPost.addHeader("cookie", "SESSION="+sessionId);
            httpPost.addHeader("Content-type", "application/json; charset=UTF-8");
            httpPost.setHeader("Accept", "application/json; charset=UTF-8");
            List<NameValuePair> nameValuePairs = new ArrayList();
            if (!CollectionUtils.isEmpty(mapData)) {
                Set keySet = mapData.keySet();
                Iterator it = keySet.iterator();

                while(it.hasNext()) {
                    String k = (String)it.next();
                    String v = String.valueOf(mapData.get(k));
                    nameValuePairs.add(new BasicNameValuePair(k, v));
                }

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return null;
    }

    public HttpHeaders settingHead(String sessionId){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("cookie", "SESSION="+sessionId);
        return headers;
    }

 public static String doPostParamToBody(String url, Map<String, String> mapData,String tokenId) {
     CloseableHttpResponse response = null;
     CloseableHttpClient httpClient = HttpClients.createDefault();
     HttpPost httpPost = new HttpPost(url);
     httpPost.setConfig(requestConfig);
     try {
    	 httpPost.addHeader("token_id", tokenId);
         httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
         List<NameValuePair> nameValuePairs = new ArrayList();
         if (!CollectionUtils.isEmpty(mapData)) {
             Set keySet = mapData.keySet();
             Iterator it = keySet.iterator();

             while(it.hasNext()) {
                 String k = (String)it.next();
                 String v = (String)mapData.get(k);
                 nameValuePairs.add(new BasicNameValuePair(k, v));
             }

             httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         }
         response = httpClient.execute(httpPost);
         HttpEntity entity = response.getEntity();
         if (entity != null) {
             return EntityUtils.toString(entity, "UTF-8");
         }
     } catch (Exception var10) {
         var10.printStackTrace();
     }

     return null;
 }



 public static String doGet(String url) throws URISyntaxException {
     String result = null;
     CloseableHttpClient httpClient = HttpClients.createDefault();
     URI uri = new URI(url);
     HttpGet get = new HttpGet(uri);
     CloseableHttpResponse response = null;

     String var7;
     try {
         response = httpClient.execute(get);
         HttpEntity entity = null;
         if (null == response) {
             return null;
         }

         if (response.getStatusLine().getStatusCode() == 200) {
             entity = response.getEntity();
             result = EntityUtils.toString(entity, "UTF-8").trim();
         }

         var7 = null != result ? result : EntityUtils.toString(response.getEntity(), "UTF-8").trim();
     } catch (IOException var18) {
         var18.printStackTrace();
         return null;
     } finally {
         try {
             httpClient.close();
             if (response != null) {
                 response.close();
             }
         } catch (IOException var17) {
             var17.printStackTrace();
         }

     }

     return var7;
 }

 public static String doGet(String url, String tokenId) throws URISyntaxException {
     String result = null;
     CloseableHttpClient httpClient = HttpClients.createDefault();
     URI uri = new URI(url);
     HttpGet get = new HttpGet(uri);
     get.addHeader("token_id", tokenId);
     CloseableHttpResponse response = null;

     String var8;
     try {
    	 get.setConfig(requestConfig);
         response = httpClient.execute(get);
         HttpEntity entity = null;
         if (null == response) {
             return null;
         }

         if (response.getStatusLine().getStatusCode() == 200) {
             entity = response.getEntity();
             result = EntityUtils.toString(entity, "UTF-8").trim();
         }

         var8 = null != result ? result : EntityUtils.toString(response.getEntity(), "UTF-8").trim();
     } catch (Exception var19) {
         var19.printStackTrace();
         return null;
     } finally {
         try {
             httpClient.close();
             if (response != null) {
                 response.close();
             }
         } catch (IOException var18) {
             var18.printStackTrace();
         }

     }

     return var8;
 }

}
