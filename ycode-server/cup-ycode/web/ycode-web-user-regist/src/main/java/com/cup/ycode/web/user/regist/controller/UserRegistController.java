package com.cup.ycode.web.user.regist.controller;

import com.cup.ycode.api.user.regist.UserRegistService;
import com.cup.ycode.commons.domain.User;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.dto.message.AbstractMessage;
import com.cup.ycode.commons.dto.message.MessageBeanFactory;
import com.cup.ycode.commons.dto.message.MessageEmail;
import com.cup.ycode.commons.dto.web.AbstractBaseController;
import com.cup.ycode.commons.utils.MapperUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserRegistController extends AbstractBaseController<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistController.class);
    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private UserRegistService userRegistService;

    @RequestMapping(value = "/registByEmail", method = RequestMethod.POST)
    @ApiOperation(value = "用户邮箱注册", notes = "使用邮箱验证码注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "account", value = "用户账号，可以用来登录的，保证唯一", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "邮箱验证码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBody registByEmailWeb(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "account") String account,
            @RequestParam(value = "verifyCode") String verifyCode,
            @RequestParam(value = "password") String password){
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");
        logger.debug("account:" + account +">>>>>>>>>>");
        logger.debug("email:" + email +">>>>>>>>>>");
        logger.debug("password:" + verifyCode +">>>>>>>>>>");
        logger.debug("password:" + password +">>>>>>>>>>");
        logger.debug("开始 调用  ycode-service-user-regist 服务提供的 registByEmail ");
        ResponseBody responseBody = userRegistService.registByEmail(account, email, verifyCode, password);
        logger.debug("调用成功， 响应对象：" +responseBody + ">>>>>>>>>>");
        response.setStatus(responseBody.getStatus());
        logger.debug("-- End --------------- "+ local +"-----------------");
        return responseBody;
    }


    @PostMapping("/sendEmailVerifyCode")
    @ApiOperation("发送邮箱注册验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBody sendEmailVerifyCode(@RequestParam(value = "email") String email){
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.info("-- Begin >>>>>>>>>> "+ local +" >>>>>>");
        logger.debug("email :" + email + ">>>>>>>>>>");
        logger.debug("开始 调用 ycode-service-user-regist 的 sendEmailVerifyCode 方法 >>>>>>>>>>");
        ResponseBody responseBody = userRegistService.sendEmailVerifyCode(email);
        logger.debug("调用成功");
        response.setStatus(responseBody.getStatus());
        logger.debug("-- End --------------- "+ local +"-----------------");
        return responseBody;
    }
}
