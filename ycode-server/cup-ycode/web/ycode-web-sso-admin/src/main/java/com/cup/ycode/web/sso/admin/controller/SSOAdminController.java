package com.cup.ycode.web.sso.admin.controller;

import com.cup.ycode.api.sso.admin.SSOAdminService;
import com.cup.ycode.commons.constant.CookieNameConstant;
import com.cup.ycode.commons.constant.HttpStatusConstant;
import com.cup.ycode.commons.domain.Admin;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.dto.web.AbstractBaseController;
import com.cup.ycode.commons.utils.CookieUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

@RestController
public class SSOAdminController extends AbstractBaseController<Admin> {
    private static final Logger logger = LoggerFactory.getLogger(SSOAdminController.class);
    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private SSOAdminService ssoAdminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "管理员登录", notes = "管理员端的单点登录入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "管理员账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    public ResponseBody login(
            @RequestParam("account") String account,
            @RequestParam("password") String password
    ){
        logger.debug("account = " + account);
        logger.debug("password = " + password);

        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            logger.error("用户名 或 密码为 null");
            return ResponseBody.error_UNAUTHORIZED_401("请输入用户名和密码");
        }

        String token = CookieUtils.getCookieValue(request, CookieNameConstant.COOKIE_NAME_ADMIN_LOGIN_TOKEN);
        logger.debug("从Cookie 中取出的 token = " + token);
        ResponseBody responseBody = ssoAdminService.login(token, account, password);
        response.setStatus(responseBody.getStatus());
        if(responseBody.getStatus() == HttpStatusConstant.OK.getStatus()){
            String resultToken = String.valueOf(responseBody.getData());
            logger.debug("登录后的 token = " + resultToken);
            CookieUtils.setCookie(request, response, CookieNameConstant.COOKIE_NAME_ADMIN_LOGIN_TOKEN, resultToken);
            logger.debug("设置了cookie 到request 中");
        }
        return responseBody;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "管理员退出登录", nickname = "前端调用此方法实现管理员的退出登录")
    public ResponseBody logout(){
        String token = CookieUtils.getCookieValue(request, CookieNameConstant.COOKIE_NAME_ADMIN_LOGIN_TOKEN);
        if(StringUtils.isBlank(token)){
            logger.debug("未登录  应该跳转至登录页面");
            return ResponseBody.error_PAYMENT_REQUIRED_402("未登录，跳转至登录页面。");
        }
        ResponseBody responseBody = ssoAdminService.logout(token);
        int status = responseBody.getStatus();
        logger.debug("响应状态码 status = " + status);
        response.setStatus(status);
        if(status == HttpStatusConstant.CREATED.getStatus()){
            logger.debug("退出登录成功  将Cookie中的 token 删除");
            CookieUtils.deleteCookie(request, response, CookieNameConstant.COOKIE_NAME_ADMIN_LOGIN_TOKEN);
            logger.debug("Cookie 中 的token 删除 完成");
        }
        return responseBody;
    }
}
