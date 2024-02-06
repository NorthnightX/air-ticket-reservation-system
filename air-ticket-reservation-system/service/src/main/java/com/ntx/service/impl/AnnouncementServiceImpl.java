package com.ntx.service.impl;

import com.ntx.entity.Announcement;
import com.ntx.mapper.AnnouncementMapper;
import com.ntx.service.AnnouncementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {
}
