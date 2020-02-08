package com.cup.ycode.api.sso.admin;

import com.cup.ycode.commons.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 管理员端的 单点登录 功能API
 */
@FeignClient(value = "ycode-service-sso-admin", fallback = SSOAdminService.SSOAdminServiceFallback.class)
public interface SSOAdminService {

    /**
     * 登录
     * @param token  登录凭据
     * @param account   账号
     * @param password  密码
     * @return  响应对象
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseBody login(@RequestParam(value = "token", required = false) String token, @RequestParam("account") String account, @RequestParam("password") String password);

    /**
     * 退出登录
     * @param token 登录凭据
     * @return  响应对象
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    ResponseBody logout(@RequestParam String token);

    /**
     * 熔断类
     */
    @Component
    static class SSOAdminServiceFallback implements SSOAdminService{
        @Override
        public ResponseBody login(String token, String account, String password) {
            return ResponseBody.error_BAD_GATEWAY_502("请求在 ycode-service-sso-admin 的 login(String token, String account, String password) 处熔断");
        }

        @Override
        public ResponseBody logout(String token) {
            return ResponseBody.error_BAD_GATEWAY_502("请求在 ycode-service-sso-admin 的 logout(String token) 处熔断");
        }
    }
}
