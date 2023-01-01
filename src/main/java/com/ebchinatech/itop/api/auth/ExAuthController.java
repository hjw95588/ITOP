package com.ebchinatech.itop.api.auth;

import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.itop.api.utils.ApiEXUtils;
import com.ebchinatech.kylin.framework.security.LoginUser;
import com.ebchinatech.kylin.framework.security.service.TokenService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.web.domain.SysUser;
import com.ebchinatech.kylin.web.domain.vo.UserVO;
import com.ebchinatech.kylin.web.mapper.SysUserDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package:com.ebchinatech.system.api.auth
 * User: Tuzki
 * Date: 2020/11/5
 * Time: 8:58
 * Description:E信认证类
 */
@RestController
@Slf4j
@RequestMapping("/kylinApi/WX")
public class ExAuthController {

    /**
     * 企业CorpId
     **/
    @Value("${ex.corpId}")
    private  String corpId;

    @Value("${ex.user.url}")
    private  String getUserInfoUrl;
    @Autowired
    private SysUserDetailMapper sysUserDetailMapper;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private ApiEXUtils  apiEXUtils;

    @RequestMapping("getUserInfo")
    public AjaxResult getUserInfo(String code) throws Exception {
        Map<String, Object> ret = new HashMap<>();
        System.out.println("getUserInfo====开始");
        String token = apiEXUtils.getToken();
        System.out.println("获取的令牌信息~~~~：" + token);
        if(StringUtils.isNotBlank(token) && StringUtils.isNotBlank(code)){
            String userInfoUrl = String.format(getUserInfoUrl,token,code);
            JSONObject userInfo = apiEXUtils.getApiData(userInfoUrl);
            if(userInfo != null){//获取用户信息成功
                String userId = userInfo.getString("UserId");
                UserVO userVO = sysUserDetailMapper.selectUserAllByUserId(userId);
                LoginUser loginUser = new LoginUser();
                SysUser sysUser = new SysUser();
                sysUser.setUserId(userVO.getUserId());
                sysUser.setUserName(userVO.getUserName());
                sysUser.setEmail(userVO.getEmail());
                sysUser.setPhonenumber(userVO.getPhonenumber());
                sysUser.setSex(userVO.getSex());
                sysUser.setDept(userVO.getDepts());
                sysUser.setRoles(userVO.getRoles());
                sysUser.setPosts(userVO.getPosts());
                loginUser.setUser(sysUser);
                return AjaxResult.success(tokenService.createToken(loginUser));
            }else{
                System.out.println("获取Userid失败！！");
            }
        }

        return AjaxResult.error("请求失败");
    }

    @RequestMapping("getSignature")
    public AjaxResult getSignature(String url) throws Exception{
        String ticket = apiEXUtils.getTicket();
        Map dataMap = new HashMap();
        if(StringUtils.isNotBlank(ticket)){
            Long timestamp = apiEXUtils.getCurrentTime(true);
            String noncestr = RandomStringUtils.randomAlphanumeric(6);
            // 参与签名的参数有四个: noncestr（随机字符串）, jsapi_ticket, timestamp（时间戳）, url（当前网页的URL， 不包含#及其后面部分）
            String string1 = "jsapi_ticket=" + ticket+"&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            //必须是按规定的加密算法
            dataMap.put("signature", DigestUtils.sha1Hex(string1));
            dataMap.put("timestamp",timestamp);
            dataMap.put("noncestr",noncestr);
            dataMap.put("corpId",corpId);
        }
        return AjaxResult.success(dataMap);
    }
}
