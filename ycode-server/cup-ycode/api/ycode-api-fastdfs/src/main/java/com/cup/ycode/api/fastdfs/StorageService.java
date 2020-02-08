package com.cup.ycode.api.fastdfs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件存储服务接口
 *
 * 此服务为公共服务，无熔断措施。 在调用本接口方法时，应自行捕获Exception，并解决
 */
@FeignClient(value = "ycode-common-fastdfs")
public interface StorageService {
    /**
     * 上传文件
     * @param data  文件的二进制内容
     * @param extName   扩展名
     * @return  上传成功后，返回文件ID， 失败返回 null
     */
    @PostMapping("/upload")
    String upload(@RequestParam("data") byte[] data, @RequestParam("extName") String extName);

    /**
     * 删除文件
     * @param fileId    被删除的文件 id
     * @return  删除成功后返回 0 ，失败后返回错误代码
     */
    @PostMapping("/delete")
    int delete(@RequestParam("fileId") String fileId);
}
