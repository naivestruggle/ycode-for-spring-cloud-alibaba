package com.cup.ycode.api.user.regist;

import com.cup.ycode.commons.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户注册服务的 功能接口
 * ycode-service-user-regist 实现接口中的具体功能
 * ycode-web-user-regist 调用接口
 *
 * 这里的 value 应当是提供实现功能的 服务名，服务名是 注册在注册中心的名字，
 *  在这里对应的就是 spring.application.name
 *
 *  fallback 是熔断机制所调用的类， 这个类必须是一个实现了 接口 的
 *
 *  本类中所有的方法参数必须加上：@RequestParam 注解，不然会报错
 *
 *  此服务为公共服务，无熔断措施。 在调用本接口方法时，应自行捕获Exception，并解决
 */
@FeignClient(value = "ycode-service-user-regist", fallback = UserRegistService.UserRegistServiceFallback.class)
public interface UserRegistService {

    /**
     * 通过邮箱验证码注册
     * @param account 用户账号
     * @param email 邮箱地址
     * @param verifyCode  邮箱验证码
     * @param password 登录密码
     * @return 结果对象
     */
    @RequestMapping(value = "/byEmail", method = RequestMethod.POST)
    ResponseBody registByEmail(@RequestParam("account") String account,
                               @RequestParam("email") String email,
                               @RequestParam("verifyCode") String verifyCode,
                               @RequestParam("password") String password);

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @return  响应对象
     */
    @PostMapping("/sendEmailVerifyCode")
    ResponseBody sendEmailVerifyCode(@RequestParam("email") String email);

    /**
     * 熔断类
     */
    @Component
    static class UserRegistServiceFallback implements UserRegistService {
        @Override
        public ResponseBody registByEmail(String account, String email, String verifyCode, String password) {
            return ResponseBody.error_BAD_GATEWAY_502("请求 ycode-service-regist#registByEmail 熔断。");
        }

        @Override
        public ResponseBody sendEmailVerifyCode(String email) {
            return ResponseBody.error_BAD_GATEWAY_502("请求 ycode-service-regist#sendEmailVerifyCode 熔断。");
        }
    }


}
