package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.Agent;
import com.atrs.airticketreservationsystem.entity.Announcement;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.service.AnnouncementService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;

    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize,
                             Announcement announcement){
        Page<Announcement> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(announcement.getTitle().length() > 0, Announcement::getTitle, announcement.getTitle());
        queryWrapper.like(announcement.getContent().length() > 0, Announcement::getContent, announcement.getContent());
        queryWrapper.like(announcement.getPublishTime() != null , Announcement::getPublishTime, announcement.getPublishTime());
        Page<Announcement> agentPage = announcementService.page(page, queryWrapper);
        return JsonResponse.success(agentPage);
    }
}
