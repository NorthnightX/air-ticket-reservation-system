package com.ntx.service.impl;

import com.ntx.entity.Baggage;
import com.ntx.mapper.BaggageMapper;
import com.ntx.service.BaggageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BaggageServiceImpl extends ServiceImpl<BaggageMapper, Baggage> implements BaggageService {
}
