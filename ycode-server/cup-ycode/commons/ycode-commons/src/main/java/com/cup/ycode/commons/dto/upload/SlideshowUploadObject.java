package com.cup.ycode.commons.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SlideshowUploadObject<T> extends AbstractUploadObject {
    private MultipartFile multipartFile;
    private T domain;
}
