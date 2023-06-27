package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.User;
import com.atrs.airticketreservationsystem.mapper.UserMapper;
import com.atrs.airticketreservationsystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
