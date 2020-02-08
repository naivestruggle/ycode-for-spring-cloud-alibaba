package com.cup.ycode.common.datetime.service.impl;

import com.cup.ycode.api.datetime.DatetimeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
public class DatetimeServiceImpl implements DatetimeService {
    /**
     * 返回当前系统的时间戳
     *
     * @return
     */
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
