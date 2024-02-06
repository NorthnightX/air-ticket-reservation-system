package com.ntx.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ntx.entity.Airport;
import com.ntx.mapper.AirportMapper;
import com.ntx.service.AirportService;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl extends ServiceImpl<AirportMapper, Airport> implements AirportService {
}
