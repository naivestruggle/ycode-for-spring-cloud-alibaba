package com.cup.ycode.commons.service.impl;

import com.cup.ycode.commons.domain.Admin;
import com.cup.ycode.commons.domain.Slideshow;
import com.cup.ycode.commons.domain.User;
import com.cup.ycode.commons.mapper.SlideshowMapper;
import com.cup.ycode.commons.mapper.UserMapper;
import com.cup.ycode.commons.service.SlideshowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.MyMapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SlideshowServiceImpl extends BaseCrudServiceImpl<Slideshow>  implements SlideshowService {
    @Autowired
    private SlideshowMapper slideshowMapper;

    /**
     * 查询 最新的几个
     *
     * @param num 查询条数
     * @return List集合
     */
    @Override
    public List<Slideshow> selectTops(int num) {
        return slideshowMapper.selectTops(num);
    }


    @Override
    public PageInfo<Slideshow> page(Slideshow domain, int pageNum, int pageSize) {
        Example example = new Example(Slideshow.class);
        example.createCriteria().andEqualTo(domain);
        if(StringUtils.isNotBlank(domain.getTitle())){
            domain.setTitle("%" + domain.getTitle() + "%");
        }
        if(StringUtils.isNotBlank(domain.getUrl())){
            domain.setUrl("%" + domain.getUrl() + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Slideshow> pageInfo = new PageInfo<>(slideshowMapper.selectByDomainOrderBy(domain));
        return pageInfo;
    }
}
