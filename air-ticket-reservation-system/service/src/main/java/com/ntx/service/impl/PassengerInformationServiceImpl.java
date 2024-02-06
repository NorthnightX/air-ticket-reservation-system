package com.ntx.service.impl;

import com.ntx.entity.PassengerInformation;
import com.ntx.mapper.PassengerInformationMapper;
import com.ntx.service.PassengerInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PassengerInformationServiceImpl extends ServiceImpl<PassengerInformationMapper, PassengerInformation> implements PassengerInformationService {
}
