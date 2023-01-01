package com.ebchinatech.itop.web.system.controller;

import com.ebchinatech.itop.common.utils.RSATool;
import com.ebchinatech.kylin.common.utils.ServletUtils;
import com.ebchinatech.kylin.framework.security.LoginUser;
import com.ebchinatech.kylin.framework.security.service.TokenService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.web.domain.SysUser;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/redirect")
public class RedirectController {
    @Value("${oldItopUrl}")
    private String oldItopUrl;

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/toItopUrl")
    public AjaxResult toItop() throws Exception{
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        //使用私钥对摘要进行加密 获得密文
        byte[] signpri_pub = RSATool.encryptByRSA1(Base64.decodeBase64(RSATool.PRIVATEKEY.getBytes(StandardCharsets.UTF_8)), (user.getUserId()+"-"+System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));

        String token= URLEncoder.encode(new String(Base64.encodeBase64(signpri_pub)),"UTF-8") ;
        return AjaxResult.success(oldItopUrl+"?token="+token);
    }
}
