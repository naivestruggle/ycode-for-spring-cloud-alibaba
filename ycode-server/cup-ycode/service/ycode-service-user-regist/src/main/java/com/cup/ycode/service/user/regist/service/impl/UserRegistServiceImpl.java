package com.cup.ycode.service.user.regist.service.impl;

import com.cup.ycode.api.redis.RedisService;
import com.cup.ycode.commons.constant.RedisKeyConstant;
import com.cup.ycode.commons.constant.TimeConstant;
import com.cup.ycode.commons.domain.User;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.dto.message.AbstractMessage;
import com.cup.ycode.commons.dto.message.MessageBeanFactory;
import com.cup.ycode.commons.dto.message.MessageEmail;
import com.cup.ycode.commons.service.UserService;
import com.cup.ycode.commons.utils.MapperUtils;
import com.cup.ycode.commons.utils.VerifyCodeUtils;
import com.cup.ycode.commons.validator.BeanValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import com.cup.ycode.api.user.regist.UserRegistService;

import javax.annotation.Resource;

/**
 * 此类 实现了 ycode-api-user-regist 中的 功能接口
 * 
 *后面 ycode-web-user-reigst 调用的时候，实际上 就是调用的这个类的方法
 * 
 * 调用是通过 Http协议调用的，所以这里要 加上 @RestController 以对外暴露API接口
 * 
 * 本类方法上可以没有 如 @RequestMapping 类的注解，默认访问的路径就是方法名
 */
@RestController
public class UserRegistServiceImpl implements UserRegistService {

    private final Logger logger = LoggerFactory.getLogger(UserRegistServiceImpl.class);
    @Value("${spring.application.name}")
    private String applicationName;
    /**
     * 通用的处理用户业务的类
     */
    @Autowired
    private UserService userService;


    /**
     * 注入一个 发送注册成功邮件的消息管道对象
     */
    @Resource
    private MessageChannel send_email_output;

    /**
     * 注入一个 发送注册验证码的消息管道对象
     */
    @Resource
    private MessageChannel send_email_verify_code_output;

    /**
     * 用来对 redis 数据库进行操作的
     */
    @Resource
    private RedisService redisService;

    /**
     * 通过邮箱验证码注册
     *
     * @param account         用户账号
     * @param email           邮箱地址
     * @param verifyCode 邮箱验证码
     * @param password        登录密码
     * @return 结果对象
     */
    @Override
    public ResponseBody registByEmail(String account,String email,String verifyCode,String password) {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();

        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");

        logger.debug("account 用户名：" + account +">>>>>>>>>>");
        logger.debug("email 邮箱：" + email +">>>>>>>>>>");
        logger.debug("verifyCode 邮箱验证码:" + verifyCode +">>>>>>>>>>");
        logger.debug("password 密码：" + password +">>>>>>>>>>");

        User user = new User();
        user.setAccount(account);
        user.setEmail(email);
        user.setPassword(password);

        // JSR303 数据校验 Begin --->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        /*
            数据校验：
                使用的 是 JSR303
                学习 SpringMVC 的时候使用过
         */
        logger.debug("开始 JSR-303 数据校验>>>>>>>>>>");
        String message = BeanValidator.validator(user);
        logger.debug("JSR-303 数据校验信息：" + message + ">>>>>>>>>>");
        if(StringUtils.isNotBlank(message)){
            logger.error("<<<<<<<<<<< JSR-303 数据校验 未通过");
            return ResponseBody.error_UNAUTHORIZED_401(message);
        }
        logger.debug("JSR-303 数据校验 通过 >>>>>>>>>>");
        //<<<<<<<<<<<--- End
        
        
        // 验证码校验 Begin --->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 调用 ycode-common-redis 服务  从redis数据库中取出验证码
        logger.debug("开始 校验 验证码 >>>>>>>>>>");
        Object data = redisService.get(RedisKeyConstant.USER_REGIST_EMAIL_VERIFY_CODE + email);
        logger.debug("从redis 中通过键("+ RedisKeyConstant.USER_REGIST_EMAIL_VERIFY_CODE + email +")取出的值：" + data + ">>>>>>>>>>");
        // 如果redis中的验证码不为 null 就将redis 中的验证码与用户输入的验证码比较
        if(data != null){
            String redisVerifyCode = String.valueOf(data);
            logger.debug("验证码校验：从redis中取出的验证码 = " + redisVerifyCode);
            // 如果redis 中的验证码 与 用户输入的验证码 不一致，那么说明验证码输入错误了
            if(! redisVerifyCode.equalsIgnoreCase(verifyCode)){
                logger.error("<<<<<<<<<< 校验失败，用户【"+email+"】输入验证码错误。");
                return ResponseBody.error_UNAUTHORIZED_401("验证码错误，请重新输入。");
            }
        }
        //如果redis中的验证码为 null ，那么说明用户没有发送验证码
        else{
            logger.error("<<<<<<<<<<redis 中没有验证码，用户【"+email+"】未发送验证码");
            return ResponseBody.error_UNAUTHORIZED_401("验证码未发送。");
        }
        logger.debug("校验 验证码 通过 >>>>>>>>>>");
        // <<<<<<<<<<<--- End
        

        // 验证用户 信息 是否重复 Begin --->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //验证用户名是否重复
        logger.debug("开始 验证用户名是否重复 >>>>>>>>>>");
        if( ! userService.unique("account", user.getAccount())){
            logger.error("<<<<<<<<<<用户名已经存在");
            return ResponseBody.error_UNAUTHORIZED_401("用户名已存在");
        }
        logger.debug("验证用户名是否重复 通过 >>>>>>>>>>");
        logger.debug("开始 验证邮箱是否重复 >>>>>>>>>>");
        //验证邮箱是否重复
        if( ! userService.unique("email", user.getEmail())){
            logger.error("<<<<<<<<<< 邮箱已存在");
            return ResponseBody.error_UNAUTHORIZED_401("邮箱已存在");
        }

        // <<<<<<<<<<<--- End

        // 注册用户 Begin --->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        logger.debug("开始 注册用户 >>>>>>>>>>");
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        User resultUser = userService.save(user);
        logger.debug("保存到数据库，返回的uesr为："+resultUser + ">>>>>>>>>>");
        if(resultUser != null){

            logger.debug("注册成功了，删除 redis 中的验证码>>>>>>>>>>");
            // 清空验证码
            redisService.del(RedisKeyConstant.USER_REGIST_EMAIL_VERIFY_CODE + email);
            logger.debug("redis中的验证码删除成功 >>>>>>>>>>");

            String subject = "Ycode";
            logger.debug("邮件标题：" + subject + ">>>>>>>>>>");

            String body = "尊敬的"+resultUser.getAccount()+ "! 欢迎加入Ycode大家庭!";
            logger.debug("邮件内容：" +body + ">>>>>>>>>>");

            String to = resultUser.getEmail();
            logger.debug("接收者：" + to + ">>>>>>>>>>");

            AbstractMessage abstractMessage = MessageBeanFactory.build(subject, body, to);
            try {
                logger.debug("发送邮件，将消息发送到 MQ >>>>>>>>>>");
                send_email_output.send(MessageBuilder.withPayload(MapperUtils.obj2json(abstractMessage)).build());
                logger.debug("发送到 MQ 成功");
            } catch (Exception e) {
                logger.error("<<<<<<<<<< 发送到 MQ 失败");
                e.printStackTrace();
                return ResponseBody.error_INSUFFICIENT_STORAGE_507(e.getMessage());
            }

            logger.debug("-- End --------------- "+ local +"-----------------");
            return ResponseBody.success_CREATED_201("注册成功！", user);
        }
        // <<<<<<<<<<<--- End

        logger.error("<<<<<<<<<< 注册失败 原因：所有的信息都校验正确了，但是没有能存入数据库。");
        // 注册失败
        return ResponseBody.error_INSUFFICIENT_STORAGE_507("注册失败，服务器繁忙，请稍后再试。");
    }

    /**
     * 发送邮箱验证码
     * 
     * 调用发送邮件服务  必须 在调用redis后面
     * 因为，如果redis服务出现异常，可能出现邮件发送出去了，但是响应内容却是不成功
     * 
     * redis 配置必须放在 bootstrap.yml 中，不然程序读不到
     *
     * @param email 邮箱
     * @return 响应对象
     */
    @Override
    public ResponseBody sendEmailVerifyCode(String email) {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.info("-- Begin >>>>>>>>>> "+ local +" >>>>>>");
        logger.debug("开始 发送邮箱注册验证码 >>>>>>>>>>");

        String subject = "Ycode 注册";
        logger.debug("标题："+subject+">>>>>>>>>>");

        String verfiyCode = VerifyCodeUtils.createEmailVerifyCode();
        logger.debug("系统生成的验证码：" +verfiyCode +">>>>>>>>>>");

        String body = "您的验证码：" + verfiyCode + ", 验证码有效期为10分钟。";
        logger.debug("邮件内容："+body+">>>>>>>>>>");

        AbstractMessage abstractMessage = MessageBeanFactory.build(subject, body, email);
        try {
            String key = RedisKeyConstant.USER_REGIST_EMAIL_VERIFY_CODE + email;
            logger.debug("在发送邮件之前，先将 生成的验证码存入 redis >>>>>>>>>>");
            redisService.put(key, verfiyCode, TimeConstant.VERIFY_CODE_TIME);
            logger.debug("存入 redis 成功 >>>>>>>>>>");

            logger.debug("将邮件发送到 MQ >>>>>>>>>>");
            send_email_verify_code_output.send(MessageBuilder.withPayload(MapperUtils.obj2json(abstractMessage)).build());
            logger.debug("发送邮件到 MQ 成功 >>>>>>>>>>");
            logger.debug("-- End --------------- "+ local +"-----------------");
            return ResponseBody.success_CREATED_201("验证码发送成功", null);
        } catch (Exception e) {
            logger.debug("<<<<<<<<<< 发送邮件失败， 具体看下面报错。");
            e.printStackTrace();
            return ResponseBody.error_INSUFFICIENT_STORAGE_507(e.getMessage());
        }
    }
}
