package com.cup.ycode.commons.web.interceptor;

import com.cup.ycode.api.redis.RedisService;
import com.cup.ycode.commons.constant.CookieNameConstant;
import com.cup.ycode.commons.constant.TimeConstant;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AdminLoginInterceptor.class);

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("admin 的登录过滤器开始");

        String token = CookieUtils.getCookieValue(request, CookieNameConstant.COOKIE_NAME_ADMIN_LOGIN_TOKEN);
        logger.debug("cookie 中获取的 token = " + token);

        ResponseBody responseBody = ResponseBody.error_PAYMENT_REQUIRED_402("你还没有登录，请登录后再试。");

        // 如果 token 不为 null, 说明可能登录了  进行下一步校验
        if(StringUtils.isNotBlank(token)){
            // 判断 token 是否存在于 redis  存在  则已登录
            logger.debug("debug + " + redisService);
            Object o = null;
            try{
                o = redisService.get(token);
                logger.debug("redis中取出的数据 o = " + o);
            }catch (Exception e){
                logger.error("redis服务出现异常");
                e.printStackTrace();
                responseBody = ResponseBody.error_INSUFFICIENT_STORAGE_507("redis服务出现异常。");
            }

            if(null != o){
                try{
                    // 为 token 续命
                    redisService.expire(token, TimeConstant.ADMIN_LOGIN_TIMEOUT);
                    logger.debug("续命成功了");
                }catch (Exception e){
                    logger.error("续命失败了");
                    e.printStackTrace();
                    return false;
                }

                return true;
            }
        }

        logger.error("未登录");

        // 未登录
        response.setCharacterEncoding("utf-8");
        response.setStatus(responseBody.getStatus());
        response.getWriter().print(responseBody);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
