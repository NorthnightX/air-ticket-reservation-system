package com.ntx.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ntx.entity.AircraftType;
import com.ntx.mapper.AircraftTypeMapper;
import com.ntx.service.AircraftTypeService;
import org.springframework.stereotype.Service;

@Service
public class AircraftTypeServiceImpl extends ServiceImpl<AircraftTypeMapper, AircraftType> implements AircraftTypeService {
}
