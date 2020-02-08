package com.cup.ycode.web.slideshow.user.controller;

import com.cup.ycode.api.slideshow.user.SlideshowUserService;
import com.cup.ycode.commons.domain.Slideshow;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.dto.web.AbstractBaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlideshowUserController extends AbstractBaseController<Slideshow>  {
    @Autowired
    private SlideshowUserService slideshowUserService;

    @GetMapping("/show")
    @ApiOperation(value = "获取最新的轮播图", notes = "获取到数据库更新的最新的几个轮播图对象")
    public ResponseBody show(){
        return slideshowUserService.show();
    }

}
