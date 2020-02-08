package com.cup.ycode.api.datetime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 此类没有使用熔断机制， 调用此类方法，需要自行进行异常捕获与处理
 */
@FeignClient(value = "ycode-common-datetime")
public interface DatetimeService {
    /**
     * 返回当前系统的时间戳
     * @return
     */
    @GetMapping("currentTimeMillis")
    long currentTimeMillis();
}
