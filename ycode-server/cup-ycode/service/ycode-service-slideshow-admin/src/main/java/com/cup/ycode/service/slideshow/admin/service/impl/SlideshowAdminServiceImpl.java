package com.cup.ycode.service.slideshow.admin.service.impl;

import com.cup.ycode.api.datetime.DatetimeService;
import com.cup.ycode.api.fastdfs.StorageService;
import com.cup.ycode.api.redis.RedisService;
import com.cup.ycode.commons.constant.RedisKeyConstant;
import com.cup.ycode.commons.domain.Slideshow;
import com.cup.ycode.commons.dto.ResponseBody;
import com.cup.ycode.commons.dto.upload.SlideshowUploadObject;
import com.cup.ycode.commons.service.SlideshowService;
import com.cup.ycode.commons.utils.MapperUtils;
import com.cup.ycode.commons.validator.BeanValidator;
import com.cup.ycode.api.slideshow.admin.SlideshowAdminService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
public class SlideshowAdminServiceImpl implements SlideshowAdminService {

    @Autowired
    private StorageService storageService;
    @Value("${fastdfs.base.url}")
    private String FASTDFS_BASE_URL;

    private static final Logger logger = LoggerFactory.getLogger(SlideshowAdminServiceImpl.class);
    @Value("${spring.application.name}")
    private String applicationName;


    @Autowired
    private DatetimeService datetimeService;

    @Autowired
    private SlideshowService slideshowService;

    @Autowired
    private RedisService redisService;

    /**
     * 往数据库插入轮播图信息
     * @param image 图片路径
     * @param title 标题
     * @param url   轮播图链接
     * @return  响应对象
     */
    @Override
    public ResponseBody insert(@RequestParam("image") String image,
                               @RequestParam("title") String title,
                               @RequestParam("url") String url) {
        Slideshow slideshow = new Slideshow();
        slideshow.setTitle(title);
        slideshow.setUrl(url);
        slideshow.setImage(image);
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");

        String message = BeanValidator.validator(slideshow);
        logger.debug("JSR-303 校验结果信息：" + message);
        if(StringUtils.isNotBlank(message)){
            logger.error("JSR-303 数据校验未通过");
            return ResponseBody.error_UNAUTHORIZED_401(message);
        }

        Long current = null;
        try{
            current = datetimeService.currentTimeMillis();
            logger.debug("获取到当前时间戳：" + current);
        }catch (Exception e){
            logger.error("获取时间戳失败：" + current);
            return ResponseBody.error_BAD_GATEWAY_502("时间服务暂时无响应。");
        }

        String hash = UUID.randomUUID().toString().replace("-", "");
        logger.debug("生成的唯一识别标志："+hash);
        slideshow.setHash(hash);

        Date create_time = new Date(current);
        logger.debug("创建时间：" + create_time);
        slideshow.setCreate_time(create_time);

        Date update_time = new Date(current);
        logger.debug("修改时间：" + update_time);
        slideshow.setUpdate_time(update_time);

        logger.debug("需要存储到数据库中的轮播图对象：" + slideshow);

        Slideshow save = slideshowService.save(slideshow);

        logger.debug("保存到数据库成功，返回的对象：" + save);

        //删除掉 redis 中缓存的 轮播图，起到更新的作用
        try{
            boolean del = redisService.del(RedisKeyConstant.SLIDESHOW_INFO_CACHE_KEY);
            if(del){
                logger.debug("删除 redis 中的轮播图缓存 成功了");
            }else{
                logger.debug("删除 redis 中的轮播图缓存 失败了，但是程序没有报错，失败原因是：缓存不存在");
            }
        }catch (Exception e){
            logger.error("删除 redis 中的轮播图缓存失败");
            e.printStackTrace();
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("上传文件成功了，但是删除缓存出了问题");
        }

        return ResponseBody.success_CREATED_201("添加轮播图成功。" , save);
    }

    /**
     * 上传轮播图
     *
     * @param multipartFile 图片对象
     * @return 响应对象
     */
    @Override
    public ResponseBody upload(@RequestPart MultipartFile multipartFile) {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");

        if(multipartFile == null){
            logger.error("上传的轮播图对象为空：multipartFile = " + multipartFile);
            return ResponseBody.error_UNAUTHORIZED_401("请选择上传的文件");
        }

        logger.debug("上传的文件对象：" + multipartFile);
        String oName = multipartFile.getOriginalFilename();
        if(StringUtils.isBlank(oName)){
            logger.error("上传的文件名为空：" + oName);
            return ResponseBody.error_UNAUTHORIZED_401("上传的文件名为空");
        }
        logger.debug("上传的文件名：" + oName);
        String extName = oName.substring(oName.lastIndexOf(".") + 1);
        logger.debug("原文件名：" + oName);
        logger.debug("文件后缀：" + extName);
        String resultUrl = null;
        try {
            String uploadUrl = storageService.upload(multipartFile.getBytes(), extName);
            logger.debug("调用上传文件服务成功，上传的文件路径为：" + uploadUrl);
            resultUrl = FASTDFS_BASE_URL + uploadUrl;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传文件失败");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("上传文件失败");
        }
        logger.debug("文件上传成功了，需要保存的路径：" + resultUrl);

        return ResponseBody.success_CREATED_201("上传成功！", resultUrl);
    }

    /**
     * 查询功能  可以显示轮播图信息
     *
     * @param slideshowObject 查询条件
     * @param pageNum   第几页
     * @param pageSize  每页显示多少条
     * @return 响应对象
     */
    @Override
    public ResponseBody select(@RequestParam("slideshow") Object slideshowObject,
                               @RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize) {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");
        logger.debug("传输过来的参数对象，被 feign 底层转换为字符串：" + slideshowObject);
        Slideshow slideshow = null;
        try {
            slideshow = MapperUtils.json2pojo(String.valueOf(slideshowObject), Slideshow.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("将传输过来的对象字符串 转为对象的过程中出现错误");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("json字符串转为对象的时候出现错误");
        }
        logger.debug("查询参数：" + slideshow);
        logger.debug("查询第几页：" + pageNum);
        logger.debug("每页显示：" + pageSize);

        if(pageNum == null || pageNum < 1){
            logger.error("参数错误 pageNum = " + pageNum);
            return ResponseBody.error_UNAUTHORIZED_401("参数错误，请输入正确的 pageNum");
        }

        if(pageSize == null || pageSize < 1){
            logger.error("参数错误 pageSize = " + pageSize);
            return ResponseBody.error_UNAUTHORIZED_401("参数错误，请输入正确的 pageSize");
        }

        if(null == slideshow){
            logger.error("参数错误 slideshow = " + slideshow);
            return ResponseBody.error_UNAUTHORIZED_401("参数错误，请输入正确的 查询参数");
        }

        PageInfo<Slideshow> page = null;
        try{
            page = slideshowService.page(slideshow, pageNum, pageSize);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("分页查询出了问题");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("分页查询异常");
        }
        logger.debug("查询到的 分页数据：page = " + page);
        if(null == page){
            logger.error("查询分页数据为null， page = " + page);
            ResponseBody.error_INSUFFICIENT_STORAGE_507("查询到分页数据为 null");
        }
        return ResponseBody.success_OK_200("分页查询成功", page);
    }

    /**
     * 更新轮播图信息
     *
     * @param id        要更新的 id
     * @param slideshowObject 更新的内容
     * @return 响应结果对象
     */
    @Override
    public ResponseBody update(@RequestParam("id") Long id,
                               @RequestParam("slideshow") Object slideshowObject) {
        // 当前所在
        String local = "|||***" + applicationName + "***" + this.getClass().getName() + "***" + Thread.currentThread() .getStackTrace()[1].getMethodName();
        logger.debug("-- Begin >>>>>>>>>> "+ local +" >>>>>>");
        logger.debug("传输过来的参数对象，被 feign 底层转换为字符串：" + slideshowObject);
        logger.debug("要修改 的 id：" + id );
        if(id == null || id < 1){
            return ResponseBody.error_UNAUTHORIZED_401("参数错误，请输入正确ID");
        }

        Slideshow slideshow = null;
        try {
            slideshow = MapperUtils.json2pojo(String.valueOf(slideshowObject), Slideshow.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("将传输过来的对象字符串 转为对象的过程中出现错误");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("json字符串转为对象的时候出现错误");
        }

        Long current = null;
        try{
            current = datetimeService.currentTimeMillis();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取当前时间失败");
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("获取时间失败");
        }

        logger.debug("获取当当前时间戳：" + current);
        slideshow.setUpdate_time(new Date(current));

        logger.debug("修改内容：" + slideshow);

        slideshow.setId(id);

        int update = slideshowService.update(slideshow);
        if(update == -1){
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("修改数据出了一点问题");
        }else if (update == 0){
            return ResponseBody.error_INSUFFICIENT_STORAGE_507("修改数据失败，修改了0条数据");
        }else if(update == 1){
            logger.debug("修改成功，接下来将 redis 缓存删除");
            try{
                redisService.del(RedisKeyConstant.SLIDESHOW_INFO_CACHE_KEY);
            }catch (Exception e){
                logger.debug("删除redis 中的缓存失败了");
                e.printStackTrace();
                return ResponseBody.error_INSUFFICIENT_STORAGE_507("修改数据成功了， 但是删除 redis 中的缓存失败了");
            }
            return ResponseBody.success_CREATED_201("修改成功", null);
        }
        return ResponseBody.error_UNAUTHORIZED_401("请正确的输入参数");
    }
}
