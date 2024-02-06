package com.ntx.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ntx.entity.JsonResponse;
import com.ntx.entity.LoginFormData;
import com.ntx.entity.User;

public interface UserService extends IService<User> {
    JsonResponse login(LoginFormData loginFormData);

    JsonResponse register(User user);
}
