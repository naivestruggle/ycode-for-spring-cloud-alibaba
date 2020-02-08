package com.cup.ycode.commons.service.impl;

import com.cup.ycode.commons.dto.web.AbstractBaseDomain;
import com.cup.ycode.commons.service.BaseCrudService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.MyMapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

public class BaseCrudServiceImpl<T extends AbstractBaseDomain> implements BaseCrudService<T> {



    private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Autowired
    protected MyMapper<T> mapper;

    /**
     * 查询是否唯一
     * @param property
     * @param value
     * @return
     */
    @Override
    public boolean unique(String property, String value) {
        Example example = new Example(entityClass);
        example.createCriteria().andEqualTo(property, value);
        int result = mapper.selectCountByExample(example);
        if (result > 0) {
            return false;
        }
        return true;
    }

    @Override
    public T save(T domain) {
        int result = 0;
        Date currentDate = new Date();
        domain.setUpdate_time(currentDate);

        // 创建
        if (domain.getId() == null) {
            domain.setCreate_time(currentDate);

            /**
             * 用于自动回显 ID，领域模型中需要 @ID 注解的支持
             * {@link AbstractBaseDomain}
             */
            result = mapper.insertUseGeneratedKeys(domain);
        }

        // 更新
        else {
            result = mapper.updateByPrimaryKey(domain);
        }

        // 保存数据成功
        if (result > 0) {
            return domain;
        }

        // 保存数据失败
        return null;
    }


    @Override
    public PageInfo<T> page(T domain, int pageNum, int pageSize) {
        Example example = new Example(entityClass);
        example.createCriteria().andEqualTo(domain);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<T> pageInfo = new PageInfo<>(mapper.selectByExample(example));
        return pageInfo;
    }


    /**
     * 根据主键修改信息
     *
     * @param domain 要修改的信息 包含主键
     * @return 结果数 -1 表示失败
     */
    @Override
    public int update(T domain) {
        Example example = new Example(entityClass);
        //按照主键进行有选择的修改
        return mapper.updateByPrimaryKeySelective(domain);
    }

    /**
     * 查询一条数据库记录
     *
     * @param domain 查询条件
     * @return 数据库记录
     */
    @Override
    public T selectOne(T domain) {
        Example example = new Example(entityClass);
        example.createCriteria().andEqualTo(domain);
        return mapper.selectOneByExample(example);
    }
}