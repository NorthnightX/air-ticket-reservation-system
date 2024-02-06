package com.ntx.service.impl;


import com.ntx.entity.Orders;
import com.ntx.mapper.OrdersMapper;
import com.ntx.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
