package com.atrs.airticketreservationsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.atrs.airticketreservationsystem.config.RabbitMQConfig;
import com.atrs.airticketreservationsystem.entity.Orders;
import com.atrs.airticketreservationsystem.service.OrdersService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.atrs.airticketreservationsystem.common.RedisConstants.ORDER_MSG;

@Service
public class OrderQueryServiceImpl {

    @Resource
    private OrdersService ordersService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 异步处理订单
     * @param msg
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void consume(String msg){
        Orders orders = JSONUtil.toBean(msg, Orders.class);
        orders.setOrderTime(String.valueOf(LocalDateTime.now()));
        //转存order数据
        String redisSaveOrderKey = ORDER_MSG + orders.getOrderId();
        Map<String, Object> orderMapById = BeanUtil.beanToMap(
                orders, new HashMap<>(), CopyOptions.create().
                        setIgnoreNullValue(true).
                        setFieldValueEditor((fieldName, fieldValue) -> {
                            if (fieldValue == null) {
                                fieldValue = "";
                            } else {
                                fieldValue = fieldValue + "";
                            }
                            return fieldValue;
                        }));
        stringRedisTemplate.opsForHash().putAll(redisSaveOrderKey, orderMapById);
        ordersService.save(orders);
    }
}
