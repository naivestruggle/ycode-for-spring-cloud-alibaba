package com.cup.ycode.commons.domain;

import com.cup.ycode.commons.dto.web.AbstractBaseDomain;
import com.cup.ycode.commons.utils.MapperUtils;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "slideshow")
@Data
public class Slideshow extends AbstractBaseDomain implements Serializable {

    /**
     * 标题
     */
    @NotNull(message = "请输入轮播图标题")
    @Length(min = 1, max = 20, message = "轮播图标题长度必须介于 1 到 20 之间")
    private String title;

    /**
     * 链接
     */
    @NotNull(message = "请输入轮播图链接")
    private String url;

    /**
     * 图片
     */
    @NotNull(message = "请输入轮播图图片地址")
    private String image;

    /**
     * 唯一标识
     */
    private String hash;

    @Override
    public String toString() {
        return super.toString();
    }

}