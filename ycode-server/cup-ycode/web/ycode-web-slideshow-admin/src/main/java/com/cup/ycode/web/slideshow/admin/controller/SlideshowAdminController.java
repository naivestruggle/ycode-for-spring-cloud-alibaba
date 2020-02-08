package com.cup.ycode.web.slideshow.admin.controller;

import com.cup.ycode.api.slideshow.admin.SlideshowAdminService;
import com.cup.ycode.commons.domain.Slideshow;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.dto.upload.SlideshowUploadObject;
import com.cup.ycode.commons.dto.web.AbstractBaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class SlideshowAdminController extends AbstractBaseController<Slideshow> {

    @Autowired
    private SlideshowAdminService slideshowAdminService;

    private static final Logger logger = LoggerFactory.getLogger(SlideshowAdminController.class);

    @ApiOperation(value = "上传轮播图", notes = "管理员上传新的轮播图信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", value = "轮播图路径", required = true,dataType = "String"),
            @ApiImplicitParam(name = "title", value = "轮播图标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "url", value = "轮播图链接", required = true, dataType = "String")
    })
    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    public ResponseBody slideshow(
             String image,
             String title,
             String url){
        logger.debug("轮播图路径：" + image);
        logger.debug("轮播图标题：" + title);
        logger.debug("轮播图链接：" + url);
        ResponseBody responseBody = slideshowAdminService.insert(image, title, url);
        response.setStatus(responseBody.getStatus());
        return responseBody;
    }

    @RequestMapping(value = "/slideshow", method = RequestMethod.PUT)
    @ApiOperation(value = "上传轮播图文件", nickname = "可以上传图片")
    public ResponseBody upload(MultipartFile multipartFile){
        logger.debug("/slideshow  上传文件 开始了");
        ResponseBody responseBody = slideshowAdminService.upload(multipartFile);
        response.setStatus(responseBody.getStatus());
        logger.debug("/slideshow  上传文件 结束了");
        return responseBody;
    }


    @ApiOperation(value = "组合条件查询", notes = "可以更据多个条件进行分页查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "title", value = "标题", required = false),
//            @ApiImplicitParam(name = "url", value = "链接", required = false),
//            @ApiImplicitParam(name = "pageNum", value = "查询第几页"),
//            @ApiImplicitParam(name = "pageSize", value = "每页显示多少条")
//    })
    @RequestMapping(value = "/slideshow", method = RequestMethod.GET)
    public ResponseBody select(
            Slideshow slideshow,
            Integer pageNum,
            Integer pageSize){
        logger.debug("查询条件：" + slideshow);
        logger.debug("查询页数：" + pageNum);
        logger.debug("每页显示多少条：" + pageSize);
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        ResponseBody responseBody = slideshowAdminService.select(slideshow, pageNum, pageSize);
        response.setStatus(responseBody.getStatus());

        return responseBody;
    }

    @RequestMapping(value = "/slideshow", method = RequestMethod.POST)
    public ResponseBody update(Long id,
                               Slideshow slideshow){
        logger.debug("id：" + id);
        if(id == null || id < 0){
            return ResponseBody.error_UNAUTHORIZED_401("参数错误，请输入正确格式的 id");
        }
        logger.debug("要修改的内容：" + slideshow);
        ResponseBody responseBody = slideshowAdminService.update(id, slideshow);
        response.setStatus(responseBody.getStatus());
        return responseBody;
    }
}
