package com.cup.ycode.api.slideshow.admin;

import com.cup.ycode.commons.domain.Slideshow;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.dto.upload.SlideshowUploadObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 管理员端 的轮播图服务 功能API
 */
@FeignClient(value = "ycode-service-slideshow-admin", fallback = SlideshowAdminService.SlideshowAdminServiceFallback.class)
public interface SlideshowAdminService {

    /**
     * 上传轮播图
     * @param multipartFile 图片对象
     * @return  响应对象
     */
    @RequestMapping(value = "/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, method = RequestMethod.PUT)
    ResponseBody upload(@RequestPart("multipartFile") MultipartFile multipartFile);

    /**
     * 往数据库插入轮播图信息
     * @param image 轮播图路径
     * @param title 标题
     * @param url   链接
     * @return  响应对象
     */
    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    ResponseBody insert(@RequestParam("image") String image,
                        @RequestParam("title") String title,
                        @RequestParam("url") String url);

    /**
     * 查询功能  可以显示轮播图信息
     * @param slideshowObject 查询条件
     * @param pageNum   第几页
     * @param pageSize  每页显示多少条
     * @return  响应对象
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    ResponseBody select(@RequestParam("slideshow") Object slideshowObject,
                      @RequestParam("pageNum") Integer pageNum,
                      @RequestParam("pageSize") Integer pageSize);

    /**
     * 更新轮播图信息
     * @param id    要更新的 id
     * @param slideshowObject 更新的内容
     * @return  响应结果对象
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ResponseBody update(@RequestParam("id") Long id,
                        @RequestParam("slideshow") Object slideshowObject);

    /**
     * 熔断机制所调用的方法
     */
    @Component
    static class SlideshowAdminServiceFallback implements SlideshowAdminService{

        @Override
        public ResponseBody upload(MultipartFile multipartFile) {
            return ResponseBody.error_BAD_GATEWAY_502("在调用 ycode-service-slideshow-admin 服务处熔断，调用方法：upload(MultipartFile multipartFile)");
        }

        @Override
        public ResponseBody insert(String image, String title, String url) {
            return ResponseBody.error_BAD_GATEWAY_502("在调用 ycode-service-slideshow-admin 服务处熔断，调用方法：insert(String image, String title, String url)");
        }

        @Override
        public ResponseBody select(Object slideshow, Integer pageNum, Integer pageSize) {
            return ResponseBody.error_BAD_GATEWAY_502("在调用 ycode-service-slideshow-admin 服务处熔断，调用方法：select(Slideshow slideshow, int pageNum, int pageSize)");
        }

        @Override
        public ResponseBody update(Long id, Object slideshowObject) {
            return ResponseBody.error_BAD_GATEWAY_502("在调用 ycode-service-slideshow-admin 服务处熔断，调用方法：update(Long id, Object slideshowObject)");
        }


    }
}
