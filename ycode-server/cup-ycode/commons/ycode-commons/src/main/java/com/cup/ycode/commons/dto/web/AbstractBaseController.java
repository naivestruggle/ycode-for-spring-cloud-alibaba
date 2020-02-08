package com.cup.ycode.commons.dto.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的控制器
 * <p>Title: AbstractBaseController</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2019/1/25 11:11
 */
public abstract class AbstractBaseController<T extends AbstractBaseDomain> {

    // 用于动态获取配置文件的属性值
    private static final String ENVIRONMENT_LOGGING_LEVEL_MY_SHOP = "logging.level.com.cup.ycode";

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    /**
     * @RequestMapper注解之上  都会执行 @ModelAttribute AOP切面
     * 可以通过这种方式 将 request, response 弄到每个方法中
     * @param request
     * @param response
     */
    @ModelAttribute
    public void initReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

}
