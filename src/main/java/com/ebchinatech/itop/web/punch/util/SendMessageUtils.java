package com.ebchinatech.itop.web.punch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @className: com.ebchinatech.itop.web.punch.util-> SendMessageUtils
 * @description: 发送邮件
 * @author: hjw
 * @createDate: 2022-06-20 0:04
 * @version: 1.0
 * @todo:
 */
@Slf4j
public class SendMessageUtils {

    public static void sendMessage(String content,String title){
        String yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //信息内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("关于 "+yyyyMMdd+" "+title);
        message.setText(content);
        message.setFrom("15736883062@163.com");//由谁发出来的
        message.setSentDate(new Date());
        message.setTo("1824633692@qq.com"); //发给谁

        //发送账号信息
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.163.com");
        mailSender.setProtocol("smtp");
        mailSender.setUsername("15736883062@163.com");
        mailSender.setPassword("XFGPQOLITBGCBWCR");
        mailSender.setDefaultEncoding("UTF-8");

        Properties p = new Properties();
        p.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(p);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            //throw new CustomException(e.getLocalizedMessage());
        }
    }
}
