package com.cup.ycode.commons.service;

import com.cup.ycode.commons.domain.Slideshow;

import java.util.List;

public interface SlideshowService extends BaseCrudService<Slideshow> {

    /**
     * 查询 最新的几个
     * @param num   查询条数
     * @return  List集合
     */
    List<Slideshow> selectTops(int num);
}
