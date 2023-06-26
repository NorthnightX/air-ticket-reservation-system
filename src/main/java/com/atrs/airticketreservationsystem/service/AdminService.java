package com.atrs.airticketreservationsystem.service;

import com.atrs.airticketreservationsystem.entity.Admin;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginForm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

public interface AdminService extends IService<Admin> {
    JsonResponse login(LoginForm loginForm);

    JsonResponse code() throws IOException;
}
