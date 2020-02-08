package com.cup.ycode.commons.service.impl;

import com.cup.ycode.commons.domain.User;
import com.cup.ycode.commons.mapper.UserMapper;
import com.cup.ycode.commons.service.BaseCrudService;
import com.cup.ycode.commons.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseCrudServiceImpl<User> implements UserService {
}
