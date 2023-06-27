package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.Agent;
import com.atrs.airticketreservationsystem.mapper.AgentMapper;
import com.atrs.airticketreservationsystem.service.AgentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService {
}
