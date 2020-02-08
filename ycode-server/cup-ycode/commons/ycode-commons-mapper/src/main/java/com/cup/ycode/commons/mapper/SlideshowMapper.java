package com.cup.ycode.commons.mapper;

import com.cup.ycode.commons.domain.Slideshow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface SlideshowMapper extends MyMapper<Slideshow> {
    @Select("select id, title, url, image, hash, create_time, update_time from slideshow ORDER BY update_time desc limit 0,#{num}")
    List<Slideshow> selectTops(@Param("num") int num);

    /**
     * 组合条件查询， 模糊查找
     * @param domain
     * @return
     */
    List<Slideshow> selectByDomainOrderBy(Slideshow domain);
}