package com.ebchinatech.itop.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;

/**
 * 数据港接口工具类
 */
@Component
public class DHClientUtils {
    // 公钥
    @Value("${dhapi.publicKey}")
    private String PUBLIC_KEY;
    // 客户端ID（与公私钥对应）
    @Value("${dhapi.clientId}")
    private String CLIENT_ID;

    /**
     * 描述: 发送请求
     *
     * @param url  请求地址
     * @param json 请求参数(Json格式字符串)
     * @return JSONObject对象
     */
    public JSONObject sendHttpRequest(String url, String json) {
        String result = HttpUtil.post(url, json);
        return new JSONObject(result);
    }
    /**
     * 描述：请求参数加密及公共参数(附加参数)封装
     *
     * @param params      请求参数(Json格式字符串) 原始请求体(JSONObject格式字符串)
     * @param isEncrypted 加密标志位 1 加密 其他情况不加密
     * @return 转换后的请求体
     */
    public String convertRequest(JSONObject params, String isEncrypted) {
        // 如需业务参数加密，则将封装好的业务参数JSON中的所有参数的值使用RSA公钥加密。也可以选择业务参数不加密。
        // 无论加不加密，都要将所有业务参数、加密标志位、当前时间戳、随机数、客户端ID、公钥的SHA-1值按照键的字母顺序连接并整体进行SHA-1加密（结果称为“签名”）。
        // 然后将加密标志位、当前时间戳、随机数、客户端ID、签名以JSON对象的形式封装到附加参数里。
        Set<String> keySet = params.keySet();
        TreeMap<String, String> extras = new TreeMap<>();
        if ("1".equals(isEncrypted)) {
            for (String key : keySet) {
                // 业务参数加密
                params.put(key, RSAEncrypt(params.getStr(key), PUBLIC_KEY));
                // 把业务参数放入附加参数中准备签名
                extras.put(key, params.getStr(key));
            }
        } else {
            for (String key : keySet) {
                // 把业务参数放入附加参数中准备签名
                extras.put(key, params.getStr(key));
            }
        }
        // 计算公钥的SHA-1值
        String keySignature = getSignature(PUBLIC_KEY);
        // 生成当前时间戳
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // 生成随机数
        StringBuilder nonce = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            Double random = Math.floor(Math.random() * 10);
            nonce.append(String.valueOf(random.intValue()));
        }
        // 把加密标志位、当前时间戳、随机数、客户端ID、公钥的SHA-1值按照键的字母顺序连接
        extras.put("is_encrypted", isEncrypted);
        extras.put("time", time);
        extras.put("nonce", nonce.toString());
        extras.put("client_id", CLIENT_ID);
        extras.put("key", keySignature);
        StringBuilder extrasBuilder = new StringBuilder();
        for (String s : extras.values()) {
            extrasBuilder.append(s);
        }
        // 整体进行SHA-1加密（结果称为“签名”）
        String signature = getSignature(extrasBuilder.toString());
        // 将签名封装到附加参数里
        extras.put("signature", signature);
        // 移除附加参数里的多余内容
        for (String key : keySet) {
            extras.remove(key);
        }
        extras.remove("key");
        // 将附加参数放入请求参数里
        params.put("extras", extras);
        return params.toString();
    }
    /**
     * 描述：根据单一参数获取签名
     *
     */
    public static String getSignature(String param) {
        StringBuilder signature = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(param.getBytes(Charset.forName("UTF-8")));
            for (byte b : digest) {
                signature.append(byteToHexStr(b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return signature.toString();
    }
    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    public static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    public static String RSAEncrypt(String content, String publicKeyBase64) {
        try {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(publicKeyBase64)));
            Cipher encodeCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            encodeCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 分段加密
            byte[] contentBytes = content.getBytes();
            int contentLength = contentBytes.length;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (contentLength - offset > 0) {
                if (contentLength - offset > 64) {
                    cache = encodeCipher.doFinal(contentBytes, offset, 64);
                } else {
                    cache = encodeCipher.doFinal(contentBytes, offset, contentLength - offset);
                }
                outputStream.write(cache, 0, cache.length);
                i++;
                offset = i * 64;
            }
            byte[] encryptedContent = outputStream.toByteArray();
            return Base64.encode(encryptedContent).replaceAll("\r|\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}