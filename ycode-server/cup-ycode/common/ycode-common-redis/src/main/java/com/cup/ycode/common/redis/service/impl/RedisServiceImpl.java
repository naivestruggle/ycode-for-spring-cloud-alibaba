package com.cup.ycode.common.redis.service.impl;

import com.cup.ycode.api.redis.RedisService;
import com.cup.ycode.commons.dto.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisServiceImpl implements RedisService {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * String  存值
     *
     * @param key     键
     * @param value   值
     * @param seconds 过期时间 单位：秒
     * @return 响应对象
     */
    @Override
    public void put(String key, Object value, Long seconds) {
        if(seconds == null){
            redisTemplate.opsForValue().set(key, value);
        }else{
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * String 取值
     *
     * @param key 键
     * @return 响应对象
     */
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除 键
     *
     * @param key 要删除的键
     * @return 删除结果
     */
    @Override
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 设置键的过期时间
     *
     * @param key     被设置的键
     * @param seconds 过期时间 单位是毫秒
     */
    @Override
    public void expire(String key, long seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.MILLISECONDS);
    }


}
