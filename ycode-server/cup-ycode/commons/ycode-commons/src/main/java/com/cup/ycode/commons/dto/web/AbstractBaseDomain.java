package com.cup.ycode.commons.dto.web;

import com.cup.ycode.commons.utils.MapperUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 通用的领域模型
 */
@Data
public class AbstractBaseDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 格式化日期，由于是北京时间（我们是在东八区），所以时区 +8
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 状态 0表示已删除  1表示可用
     */
    private String status;

    @Override
    public String toString() {
        try {
            return MapperUtils.obj2json(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
