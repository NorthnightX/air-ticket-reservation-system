package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.Agent;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.Vip;
import com.atrs.airticketreservationsystem.service.VipService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/vip")
public class VipController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private VipService vipService;

    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize)
    {
        Page<Vip> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Vip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Vip::getLevel);
        Page<Vip> vipPage = vipService.page(page, queryWrapper);
        return JsonResponse.success(vipPage);
    }
}
