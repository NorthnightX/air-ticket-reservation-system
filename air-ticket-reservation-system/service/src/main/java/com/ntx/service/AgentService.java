package com.ntx.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ntx.entity.Agent;
import com.ntx.entity.JsonResponse;
import com.ntx.entity.LoginFormData;

public interface AgentService extends IService<Agent> {
    JsonResponse login(LoginFormData loginFormData);
}
