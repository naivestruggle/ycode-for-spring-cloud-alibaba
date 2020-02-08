package com.cup.ycode.commons.service;

import com.cup.ycode.commons.dto.web.AbstractBaseDomain;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用的业务逻辑
 * <p>Title: BaseCrudService</p>
 * <p>Description: </p>
 */
public interface BaseCrudService<T extends AbstractBaseDomain> {

    /**
     * 查询属性值是否唯一
     *
     * @param property
     * @param value
     * @return true/唯一，false/不唯一
     */
    default boolean unique(String property, String value) {
        return false;
    }

    /**
     * 保存
     * @param domain
     * @return
     */
    default T save(T domain) {
        return null;
    }

    /**
     * 分页查询
     * @param domain
     * @param pageNum
     * @param pageSize
     * @return
     */
    default PageInfo<T> page(T domain, int pageNum, int pageSize){
        return null;
    }

    /**
     * 根据主键修改信息
     * @param domain    要修改的信息  里面包含主键
     * @return  结果数 -1 表示失败
     */
    default int update(T domain){return -1;};


    /**
     * 查询一条数据库记录
     * @param domain    查询条件
     * @return  数据库记录
     */
    default T selectOne(T domain){return null;}


}