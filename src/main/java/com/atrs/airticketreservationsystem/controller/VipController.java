package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.service.VipService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/vip")
public class VipController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private VipService vipService;


}
