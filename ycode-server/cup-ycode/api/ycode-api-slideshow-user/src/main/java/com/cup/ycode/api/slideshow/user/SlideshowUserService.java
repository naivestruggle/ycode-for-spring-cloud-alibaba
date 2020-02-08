package com.cup.ycode.api.slideshow.user;

import com.cup.ycode.commons.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户端的 轮播图 功能API
 */
@FeignClient(value = "ycode-service-slideshow-user", fallback = SlideshowUserService.SlideshowUserServiceFallback.class)
public interface SlideshowUserService {

    /**
     * 显示 数据库中 最新的几条
     * @return
     */
    @GetMapping("/show")
    ResponseBody show();

    /**
     * 熔断机制
     */
    @Component
    static class SlideshowUserServiceFallback implements SlideshowUserService{
        @Override
        public ResponseBody show() {
            return ResponseBody.error_BAD_GATEWAY_502("请求在 ycode-service-slideshow-user 的 show() 方法处熔断");
        }
    }
}
