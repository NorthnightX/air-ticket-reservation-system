package com.ntx.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ntx.entity.AircraftInformation;
import com.ntx.mapper.AircraftInformationMapper;
import com.ntx.service.AircraftInformationService;
import org.springframework.stereotype.Service;

@Service
public class AircraftInformationServiceImpl extends ServiceImpl<AircraftInformationMapper, AircraftInformation> implements AircraftInformationService {
}
