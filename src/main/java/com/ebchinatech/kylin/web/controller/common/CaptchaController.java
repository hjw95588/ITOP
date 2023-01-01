//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ebchinatech.kylin.web.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.kylin.common.constant.Constants;
import com.ebchinatech.kylin.common.utils.IdUtils;
import com.ebchinatech.kylin.common.utils.MessageUtils;
import com.ebchinatech.kylin.common.utils.StringUtils;
import com.ebchinatech.kylin.common.utils.VerifyCodeUtils;
import com.ebchinatech.kylin.common.utils.sign.Base64;
import com.ebchinatech.kylin.framework.redis.RedisCache;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.web.domain.SysMail;
import com.ebchinatech.kylin.web.service.ISysMailService;
import com.ebchinatech.kylin.web.service.ISysUserService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysMailService sysMailService;
    @Autowired
    private ISysUserService userService;
    @Value("${kylin.name}")
    private String sysName;

    public CaptchaController() {
    }
    @GetMapping({"/captchaImage"})
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        String uuid = IdUtils.simpleUUID();
        String verifyKey = "captcha_codes:" + uuid;
        this.redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        int w = 111;
        int h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);

        AjaxResult var9;
        try {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("uuid", uuid);
            ajax.put("img", Base64.encode(stream.toByteArray()));
            ajax.put("imageCode", (String)this.redisCache.getCacheObject(verifyKey));
            var9 = ajax;
            return var9;
        } catch (Exception var13) {
            var13.printStackTrace();
            var9 = AjaxResult.error(var13.getMessage());
        } finally {
            stream.close();
        }

        return var9;
    }

    @PostMapping({"/captchaMailCode"})
    public AjaxResult getCaptchaMailCode(@RequestBody String msg) {
        JSONObject data = JSON.parseObject(msg);
        if (Objects.isNull(data)) {
            return AjaxResult.error();
        } else {
            String email = data.getString("email");
            if (StringUtils.isEmpty(email)) {
                return AjaxResult.error();
            } else {
                String verifyCode = VerifyCodeUtils.generateVerifyCode(6);
                String uuid = IdUtils.simpleUUID();
                String verifyKey = "captcha_mail_regist_codes:" + uuid;
                String emailKey = "captcha_mail_regist_email:" + email;
                if (!StringUtils.isEmpty((String)this.redisCache.getCacheObject(emailKey))) {
                    return AjaxResult.error(MessageUtils.message("user.regist.email.captcha.notExpire", new Object[0]));
                } else {
                    this.redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_MAIL_REGIST_EXPIRATION, TimeUnit.MINUTES);
                    this.redisCache.setCacheObject(emailKey, email, Constants.CAPTCHA_MAIL_REGIST_EXPIRATION, TimeUnit.MINUTES);
                    SysMail sysMail = (new SysMail()).setReceivers(email).setMailType(0).setSubject(MessageUtils.message("user.regist.subject", new Object[]{this.sysName})).setText(StringUtils.format(MessageUtils.message("user.regist.info", new Object[0]), new Object[]{verifyCode, Constants.CAPTCHA_MAIL_REGIST_EXPIRATION}));
                    boolean isSuccess = this.sysMailService.send(sysMail);
                    if (!isSuccess) {
                        return AjaxResult.error(MessageUtils.message("user.regist.email.captcha.send.error", new Object[0]));
                    } else {
                        AjaxResult ajax = AjaxResult.success();
                        ajax.put("uuid", uuid);
                        return ajax;
                    }
                }
            }
        }
    }

    @PostMapping({"/forgetPwdCaptchaMailCode"})
    public AjaxResult getForgetPwdCaptchaMailCode(@RequestBody String msg) {
        JSONObject data = JSON.parseObject(msg);
        if (Objects.isNull(data)) {
            return AjaxResult.error();
        } else {
            String userId = data.getString("userId");
            String email = data.getString("email");
            if (StringUtils.isAnyEmpty(new CharSequence[]{userId, email})) {
                return AjaxResult.error();
            } else if (Objects.equals("1", this.userService.checkUserIdAndEmailUnique(userId, email))) {
                return AjaxResult.error(10104, MessageUtils.message("user.email.invalid", new Object[0]));
            } else {
                String verifyCode = VerifyCodeUtils.generateVerifyCode(6);
                String uuid = IdUtils.simpleUUID();
                String verifyKey = "captcha_mail_forget_pwd_codes:" + uuid;
                String emailKey = "captcha_mail_forget_pwd_email:" + email;
                if (!StringUtils.isEmpty((String)this.redisCache.getCacheObject(emailKey))) {
                    return AjaxResult.error(MessageUtils.message("user.regist.email.captcha.notExpire", new Object[0]));
                } else {
                    this.redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_MAIL_FORGET_PWD_EXPIRATION, TimeUnit.MINUTES);
                    this.redisCache.setCacheObject(emailKey, email, Constants.CAPTCHA_MAIL_FORGET_PWD_EXPIRATION, TimeUnit.MINUTES);
                    SysMail sysMail = (new SysMail()).setReceivers(email).setMailType(0).setSubject(MessageUtils.message("user.forget.pwd.subject", new Object[]{this.sysName})).setText(StringUtils.format(MessageUtils.message("user.forget.pwd.info", new Object[0]), new Object[]{verifyCode, Constants.CAPTCHA_MAIL_FORGET_PWD_EXPIRATION}));
                    boolean isSuccess = this.sysMailService.send(sysMail);
                    if (!isSuccess) {
                        return AjaxResult.error(MessageUtils.message("user.regist.email.captcha.send.error", new Object[0]));
                    } else {
                        AjaxResult ajax = AjaxResult.success();
                        ajax.put("uuid", uuid);
                        return ajax;
                    }
                }
            }
        }
    }
}
