package com.ntx.service;




import com.baomidou.mybatisplus.extension.service.IService;
import com.ntx.entity.Administrator;
import com.ntx.entity.JsonResponse;
import com.ntx.entity.LoginFormData;

import java.io.IOException;

public interface AdminService extends IService<Administrator> {
    JsonResponse login(LoginFormData loginForm);

    JsonResponse code() throws IOException;
}
