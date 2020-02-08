package com.cup.ycode.service.sso.admin.service.impl;

import com.cup.ycode.api.redis.RedisService;
import com.cup.ycode.api.sso.admin.SSOAdminService;
import com.cup.ycode.commons.constant.TimeConstant;
import com.cup.ycode.commons.domain.Admin;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.service.AdminService;
import com.cup.ycode.commons.utils.CookieUtils;
import com.cup.ycode.commons.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SSOAdminServiceImpl implements SSOAdminService {
    private static final Logger logger = LoggerFactory.getLogger(SSOAdminServiceImpl.class);
    @Value("${spring.application.name}")
    private String applicationName;


    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseBody login(@RequestParam(value = "token", required = false) String token,
                              @RequestParam("account") String account,
                              @RequestParam("password") String password) {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");

        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            logger.error("账号：account = " + account + ", 密码：password = " + password);
            return ResponseBody.error_UNAUTHORIZED_401("请输入账号和密码。");
        }

        logger.debug("account = " + account);
        logger.debug("password = " + password);
        password = DigestUtils.md5DigestAsHex(password.getBytes());


        // 看看 此管理员是否已经登录过，如果登录过，直接返回令牌
        if(StringUtils.isNotBlank(token)){
            Object obj = null;
            try{
                logger.debug("当前的 redisService = " + redisService);
                obj = redisService.get(token);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseBody.error_INSUFFICIENT_STORAGE_507("redis 服务未启动！");
            }

            if(obj != null){
                logger.debug("redis中还有对应的数据， 表示已经登录成功了，直接返回即可。");
                return ResponseBody.success_OK_200("登录成功！", token);
            }
        }

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setPassword(password);

        Admin loginedAdmin = adminService.selectOne(admin);

        if(loginedAdmin == null){
            logger.error("登陆失败，用户名或密码不正确。");
            return ResponseBody.error_UNAUTHORIZED_401("登陆失败，用户名或密码不正确。");
        }
        logger.debug("登陆成功：admin = " + loginedAdmin);

        String newToken = UUIDUtils.getUUID();
        logger.debug("生成的登录 token = " + newToken);

        try{
            redisService.put(newToken, loginedAdmin, TimeConstant.ADMIN_LOGIN_TIMEOUT);
            logger.debug("将 token 存储到 redis 成功");
        }catch (Exception e){
            logger.error("将 token 存储到 redis 失败 ， 是 redisService 的原因");
            e.printStackTrace();
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("token 存储失败");
        }

        // 登录成功  token 也存储成功了
        return ResponseBody.success_OK_200("登录成功！", newToken);
    }

    /**
     * 退出登录
     *
     * @param token 登录凭据
     * @return 响应对象
     */
    @Override
    public ResponseBody logout(String token) {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");

        logger.debug("登录凭据 token = " + token);
        if(StringUtils.isBlank(token)){
            logger.error("没有 token 说明未登录，怎么会跳转到 退出登录 服务呢？逻辑不正确");
            return ResponseBody.error_PAYMENT_REQUIRED_402("未登录");
        }
        boolean del = false;
        try{
            del = redisService.del(token);
            logger.debug("删除结果 del = " + del);
        }catch (Exception e){
            logger.error("redis服务出现问题");
            e.printStackTrace();
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("redis服务出现异常");
        }

        if(del){
            return ResponseBody.success_CREATED_201("退出登录成功。", null);
        }else{
            logger.error("没有 token 说明未登录，怎么会跳转到 退出登录 服务呢？逻辑不正确");
            return ResponseBody.error_PAYMENT_REQUIRED_402("未登录");
        }
    }

}
