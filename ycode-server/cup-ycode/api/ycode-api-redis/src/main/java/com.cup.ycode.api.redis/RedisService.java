package com.cup.ycode.api.redis;

import com.cup.ycode.commons.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 此服务为公共服务，无熔断措施。 在调用本接口方法时，应自行捕获Exception，并解决
 */
@FeignClient(value = "ycode-common-redis")
public interface RedisService {
    /**
     * String  存值
     * @param key   键
     * @param value 值
     * @param seconds 过期时间 单位：毫秒
     */
    @PostMapping("/put")
    void put(@RequestParam("key") String key,
             @RequestParam("value") Object value,
             @RequestParam("seconds") Long seconds);

    /**
     * String 取值
     * @param key   键
     * @return  值
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    Object get(@RequestParam("key") String key);

    /**
     * 删除 键
     * @param key   要删除的键
     * @return  删除结果
     */
    @PostMapping("/del")
    boolean del(@RequestParam("key") String key);

    /**
     * 设置键的过期时间
     * @param key   被设置的键
     * @param seconds   过期时间 单位是毫秒
     */
    @PostMapping("/expire")
    void expire(@RequestParam("key") String key, @RequestParam("seconds") long seconds);
}
