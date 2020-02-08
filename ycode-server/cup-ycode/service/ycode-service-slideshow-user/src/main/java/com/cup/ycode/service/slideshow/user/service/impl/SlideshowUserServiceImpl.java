package com.cup.ycode.service.slideshow.user.service.impl;

import com.cup.ycode.api.redis.RedisService;
import com.cup.ycode.api.slideshow.user.SlideshowUserService;
import com.cup.ycode.commons.constant.RedisKeyConstant;
import com.cup.ycode.commons.domain.Slideshow;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.service.SlideshowService;
import com.cup.ycode.commons.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@RestController
public class SlideshowUserServiceImpl implements SlideshowUserService {

    private static final Logger logger = LoggerFactory.getLogger(SlideshowUserServiceImpl.class);
    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SlideshowService slideshowService;
    /**
     * 显示 数据库中 最新的几条
     *
     * @return
     */
    @Override
    public ResponseBody show() {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");

        try{
            logger.debug("开始从 redis 中取缓存数据");
            Object data = redisService.get(RedisKeyConstant.SLIDESHOW_INFO_CACHE_KEY);
            logger.debug("取出的缓存数据：data = " + data);
            if(data != null){
                List<Slideshow> slideshowList = (List<Slideshow>) data;
                logger.debug("data 不为null ，转为对象：slideshowList = " + slideshowList);
                return ResponseBody.success_OK_200("请求成功，获得了数据", slideshowList);
            }
            logger.debug("redis 中没有缓存");
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("从 redis 读取缓存失败");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("读取缓存数据出现问题");
        }

        // redis中没有缓存数据  要从数据库拿
        List<Slideshow> slideshowList = null;
        try{
            slideshowList = slideshowService.selectTops(4);
            if(null == slideshowList){
                logger.error("查询数据为 null 这不正常");
                return ResponseBody.error_INSUFFICIENT_STORAGE_507("数据为 null");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询数据库出现问题");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("数据库出现问题");
        }

        //数据正常了
        logger.debug("查询出来的数据："+ slideshowList);
        //存入 redis 缓存中
        try {
            redisService.put(RedisKeyConstant.SLIDESHOW_INFO_CACHE_KEY, slideshowList, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据存储到 redis 缓存中失败");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("数据存储缓存失败");
        }

        logger.debug("数据存储到 redis 缓存中成功了");

        return ResponseBody.success_OK_200("请求成功，获得了数据", slideshowList);
    }
}
