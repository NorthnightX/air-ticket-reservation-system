package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.service.AgentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/agent")
public class AgentController {
    @Resource
    private AgentService agentService;
}
