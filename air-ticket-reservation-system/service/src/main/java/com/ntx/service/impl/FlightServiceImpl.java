package com.ntx.service.impl;

import com.ntx.entity.Flight;
import com.ntx.mapper.FlightMapper;
import com.ntx.service.FlightService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl extends ServiceImpl<FlightMapper, Flight> implements FlightService {
}
