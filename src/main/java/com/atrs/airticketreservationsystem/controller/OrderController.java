package com.atrs.airticketreservationsystem.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.atrs.airticketreservationsystem.config.RabbitMQConfig;
import com.atrs.airticketreservationsystem.dto.OrderDto;
import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.*;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.atrs.airticketreservationsystem.common.RedisConstants.*;
import static com.atrs.airticketreservationsystem.common.SystemConstants.IS_USE_TICKET;
import static com.atrs.airticketreservationsystem.common.SystemConstants.LATEST_CANCEL;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrdersService orderService;
    @Resource
    private FlightService flightService;
    @Resource
    private RouteService routeService;
    @Resource
    private AircraftInformationService aircraftInformationService;
    @Resource
    private AirportService airportService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BaggageService baggageService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserService userService;

    /**
     * 条件查询
     * @param pageNum
     * @param pageSize
     * @param order
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse queryAll(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            Orders order) {
        LambdaQueryWrapper<Orders> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<Orders> page = new Page<>(pageNum, pageSize);

        // 订单时间条件查询
        if (order.getOrderTime() != null && !order.getOrderTime().isEmpty()) {
            orderLambdaQueryWrapper.like(Orders::getOrderTime, order.getOrderTime());
        }

        // 根据出发地和目的地机场ID获取航线
        List<Route> routes = getRoutes(order);
        List<Long> routeIds = routes.stream().map(Route::getId).collect(Collectors.toList());
        // 根据航线查询航班
        if (!routeIds.isEmpty()) {
            LambdaQueryWrapper<Flight> flightLambdaQueryWrapper = new LambdaQueryWrapper<>();
            flightLambdaQueryWrapper.in(Flight::getRouteId, routeIds);
            List<Flight> flightList = flightService.list(flightLambdaQueryWrapper);
            List<Long> flightIds = flightList.stream().map(Flight::getFlightId).collect(Collectors.toList());
            orderLambdaQueryWrapper.in(Orders::getFlightId, flightIds);
        }
        Page<Orders> orderPage = orderService.page(page, orderLambdaQueryWrapper);
        List<Orders> orders = orderPage.getRecords();
        populateOrder(orders);
        return JsonResponse.success(orderPage);
    }

    /**
     * 查询订单对应航班的路线
     * @param order
     * @return
     */
    private List<Route> getRoutes(Orders order) {
        LambdaQueryWrapper<Route> routeLambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 构建出发地和目的地查询条件
        if (order.getDepartureAirportId() != null) {
            routeLambdaQueryWrapper.eq(Route::getDepartureAirportId, order.getDepartureAirportId());
        }
        if (order.getDestinationAirportId() != null) {
            routeLambdaQueryWrapper.eq(Route::getDestinationAirportId, order.getDestinationAirportId());
        }
        return routeService.list(routeLambdaQueryWrapper);
    }

    /**
     * 订单回显数据赋值
     * @param orders
     */
    private void populateOrder(List<Orders> orders) {
        // 通过order得到flight的id
        Set<Long> flightIds = orders.stream().map(Orders::getFlightId).collect(Collectors.toSet());
        List<Flight> flightList = flightService.listByIds(flightIds);
        // 通过flight得到aircraftIds
        List<Long> aircraftIds = flightList.stream().map(Flight::getAircraftId).collect(Collectors.toList());
        // 得到AircraftInformation的集合
        Map<Long, AircraftInformation> aircraftMap = aircraftInformationService.listByIds(aircraftIds).stream()
                .collect(Collectors.toMap(AircraftInformation::getId, Function.identity()));
        // 得到route集合
        List<Long> routeIds = flightList.stream().map(Flight::getRouteId).collect(Collectors.toList());
        List<Route> routeList = routeService.listByIds(routeIds);
        // 得到出发机场和目的机场的ids
        List<Long> destinationAirportIds = routeList.stream().map(Route::getDestinationAirportId).toList();
        List<Long> departureAirportIds = routeList.stream().map(Route::getDepartureAirportId).toList();
        Map<Long, Airport> departureAirport = airportService.listByIds(departureAirportIds).stream()
                .collect(Collectors.toMap(Airport::getId, Function.identity()));
        Map<Long, Airport> destinationAirport = airportService.listByIds(destinationAirportIds).stream()
                .collect(Collectors.toMap(Airport::getId, Function.identity()));
        //赋值
        for (Orders order : orders) {
            Long flightId = order.getFlightId();
            Flight flight = flightList.stream().filter(f -> f.getFlightId().equals(flightId)).findFirst().orElse(null);
            if (flight != null) {
                Long aircraftId = flight.getAircraftId();
                AircraftInformation aircraft = aircraftMap.get(aircraftId);
                if (aircraft != null) {
                    order.setAircraftId(aircraftId);
                    order.setAircraftCode(aircraft.getAircraftCode());
                }
                Long routeId = flight.getRouteId();
                Route route = routeList.stream().filter(r -> r.getId().equals(routeId)).findFirst().orElse(null);
                if (route != null) {
                    order.setDepartureAirportId(route.getDepartureAirportId());
                    order.setDestinationAirportId(route.getDestinationAirportId());

                    Airport departure = departureAirport.get(route.getDepartureAirportId());
                    if (departure != null) {
                        order.setDepartureAirport(departure.getAirportName());
                        order.setDepartureCity(departure.getCity());
                    }
                    Airport destination = destinationAirport.get(route.getDestinationAirportId());
                    if (destination != null) {
                        order.setDestinationAirport(destination.getAirportName());
                        order.setDestinationCity(destination.getCity());
                    }
                }
                Map<String, Object> orderMap = BeanUtil.beanToMap(
                        order, new HashMap<>(), CopyOptions.create().
                                setIgnoreNullValue(true).
                                setFieldValueEditor((fieldName, fieldValue) -> {
                                    if (fieldValue == null) {
                                        fieldValue = "";
                                    } else {
                                        fieldValue = fieldValue + "";
                                    }
                                    return fieldValue;
                                }));
                String redisKey = ORDER_MSG + orderMap.get("orderId");
                stringRedisTemplate.opsForHash().putAll(redisKey, orderMap);
            }
        }
    }

    /**
     * 修改订单
     * @param orders
     * @return
     * @throws ParseException
     */
    @PutMapping("/updateOrder")
    public JsonResponse updateOrder(@RequestBody Orders orders) throws ParseException {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getOrderId, orders.getOrderId());
        boolean updated = orderService.update(orders, lambdaQueryWrapper);
        if (!updated) {
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @Transactional
    @PostMapping("/delete/{orderId}")
    public JsonResponse delete(@PathVariable Long orderId) {
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Baggage> baggageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getOrderId, orderId);
        baggageLambdaQueryWrapper.eq(Baggage::getTicketId, orderId);
        baggageService.remove(baggageLambdaQueryWrapper);
        boolean delete = orderService.remove(ordersLambdaQueryWrapper);
        if (!delete) {
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 买票
     * @param orders
     * @return
     */
    @Transactional
    @PostMapping("/purchase")
    public JsonResponse purchase(@RequestBody OrderDto orders) {
        List<Long> idList = new ArrayList<>();
        for(int i = 0; i < orders.getOrders().size(); i++){
            Orders order = orders.getOrders().get(i);
            String seatType = order.getSeatType();
            Long flightId = order.getFlightId();
            String redisKey = FLIGHT_MSG + flightId;
            //获取缓存中航班信息
            Map<Object, Object> flightMap = stringRedisTemplate.opsForHash().entries(redisKey);
            //如果缓存该航班信息
            if(!flightMap.isEmpty()){
                //获取用户选择的座位的座位数量
                int availableSeats;
                if (seatType.equals("0")) {
                    availableSeats = Integer.parseInt((String) flightMap.get("economyClassNum"));
                } else {
                    availableSeats = Integer.parseInt((String) flightMap.get("firstClassNum"));
                }
                //查询票数
                if (availableSeats < 1 && i == 0) {
                    return JsonResponse.error("购票失败，没有余票");
                } else if (i > 0 && availableSeats < 1) {
                    return JsonResponse.error("票已售空，购买已购买票" + (i + 1) + "张");
                }else {
                    //有余票，修改航班信息
                    String seatTypeKey = seatType.equals("0") ? "economy_class_num" : "first_class_num";
                    String seatTypeKeyRedis = seatType.equals("0") ? "economyClassNum" : "firstClassNum";
                    stringRedisTemplate.opsForHash().put(redisKey, seatTypeKeyRedis, String.valueOf(availableSeats - 1));
                    boolean update = flightService.update()
                            .setSql(seatTypeKey + " = " + seatTypeKey + " - 1")
                            .eq("flight_id", flightId)
                            .gt(seatTypeKey, 0)
                            .update();
                    if (!update && i > 0) {
                        return JsonResponse.error("票已售空，购买已购买票" + (i + 1) + "张");
                    } else if (i == 0 && !update) {
                        return JsonResponse.error("购票失败，没有余票");
                    }
                }
//                //如果乘客选择的是经济仓
//                if(seatType.equals("0")){
//                    Integer economyClassNum = Integer.parseInt((String) flightMap.get("economyClassNum"));
//                    if (economyClassNum < 1 && i == 0) {
//                        return JsonResponse.error("购票失败，没有余票");
//                    } else if (i > 0 &&economyClassNum < 1) {
//                        return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                    }
//                    else{
//                        stringRedisTemplate.opsForHash().put(redisKey, "economyClassNum", String.valueOf(economyClassNum - 1));
//                        boolean update = flightService.update().setSql("economy_class_num = economy_class_num - 1").
//                                eq("flight_id", flightId).gt("economy_class_num", 0).update();
//                        if (!update && i > 0) {
//                            return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                        } else if(i == 0 && !update){
//                            return JsonResponse.error("购票失败，没有余票");
//                        }
//                    }
//                }
//                //如果乘客选择的是头等舱
//                else if(seatType.equals("1")){
//                    Integer firstClassNum = Integer.parseInt((String) flightMap.get("firstClassNum"));
//                    if (firstClassNum < 1 && i == 0) {
//                        return JsonResponse.error("购票失败，没有余票");
//                    } else if (i > 0 && firstClassNum < 1) {
//                        return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                    }
//                    else {
//                        stringRedisTemplate.opsForHash().put(redisKey, "firstClassNum", String.valueOf(firstClassNum - 1));
//                        boolean update = flightService.update().setSql("first_class_num = first_class_num - 1").
//                                eq("flight_id", flightId).gt("first_class_num", 0).update();
//                        if (!update && i > 0) {
//                            return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                        } else if(i == 0 && !update){
//                            return JsonResponse.error("购票失败，没有余票");
//                        }
//                    }
//                }
            }
            //缓存中没有
            else{
                LambdaQueryWrapper<Flight> flightLambdaQueryWrapper = new LambdaQueryWrapper<>();
                flightLambdaQueryWrapper.eq(Flight::getFlightId, flightId);
                Flight flight = flightService.getOne(flightLambdaQueryWrapper);
                //查询航班，没有直接返回
                if(flight == null){
                    return JsonResponse.error("没有该航班");
                }
                //判断用户选择的座位类型
                int availableSeats;
                if (seatType.equals("0")) {
                    availableSeats = flight.getEconomyClassNum();
                } else {
                    availableSeats = flight.getFirstClassNum();
                }
                //判断余票和已购买次数
                if (availableSeats < 1 && i == 0) {
                    return JsonResponse.error("购票失败，没有余票");
                } else if (i > 0 && availableSeats < 1) {
                    return JsonResponse.error("票已售空，购买已购买票" + (i + 1) + "张");
                } else {
                    //有票，修改航班信息
                    String seatTypeKey = seatType.equals("0") ? "economy_class_num" : "first_class_num";
                    boolean update = flightService.update()
                            .setSql(seatTypeKey + " = " + seatTypeKey + " - 1")
                            .eq("flight_id", flightId)
                            .gt(seatTypeKey, 0)
                            .update();
                    if (!update && i > 0) {
                        return JsonResponse.error("票已售空，购买已购买票" + (i + 1) + "张");
                    } else if (i == 0 && !update) {
                        return JsonResponse.error("购票失败，没有余票");
                    }
                }
//                if (seatType.equals("0")) {
//                    Integer economyClassNum = flight.getEconomyClassNum();
//                    if (economyClassNum < 1 && i == 0) {
//                        return JsonResponse.error("购票失败，没有余票");
//                    } else if (i > 0 &&economyClassNum < 1) {
//                        return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                    } else {
//                        boolean update = flightService.update().setSql("economy_class_num = economy_class_num - 1").
//                                eq("flight_id", flightId).gt("economy_class_num", 0).update();
//                        if (!update && i > 0) {
//                            return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                        } else if(i == 0 && !update){
//                            return JsonResponse.error("购票失败，没有余票");
//                        }
//                    }
//                }
//                else if(seatType.equals("1")){
//                    Integer firstClassNum = flight.getFirstClassNum();
//                    if (firstClassNum < 1 && i == 0) {
//                        return JsonResponse.error("购票失败，没有余票");
//                    } else if (i > 0 && firstClassNum < 1) {
//                        return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                    }
//                    else {
//                        boolean update = flightService.update().setSql("first_class_num = first_class_num - 1").
//                                eq("flight_id", flightId).gt("first_class_num", 0).update();
//                        if (!update && i > 0) {
//                            return JsonResponse.error("票已售空,购买已购买票" + i + 1 + "张");
//                        } else if(i == 0 && !update){
//                            return JsonResponse.error("购票失败，没有余票");
//                        }
//                    }
//                }
            }
            Orders orderSuccess = new Orders();
            BeanUtils.copyProperties(order, orderSuccess);
            //根据雪花算法生成id
            Snowflake snowflakeId = IdUtil.getSnowflake(RandomUtil.randomInt(0, 31));
            long id = snowflakeId.nextId();
            //设置order的订单号
            orderSuccess.setOrderId(id);
            //在此处给购买人赋值，消息队列购买会开启新的线程
            orderSuccess.setBookingPerson(Math.toIntExact(UserHolder.getUser().getId()));
            System.out.println(UserHolder.getUser().getId());
            //将orderSuccess转化成json发给消息队列处理
            String jsonStr = JSONUtil.toJsonStr(orderSuccess);
            System.out.println(orderSuccess);
            rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_KEY,jsonStr);
            idList.add(id);
        }
        return JsonResponse.success("购买完成:订单号是" + idList);
    }





    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @Transactional
    @PostMapping("/returnTicket/{orderId}")
    public JsonResponse returnTicket(@PathVariable Long orderId) throws ParseException {
        try {
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Orders::getOrderId, orderId);
            Orders orders = orderService.getOne(queryWrapper);
            //获取订单的航班id
            Long flightId = orders.getFlightId();
            LambdaQueryWrapper<Flight> flightLambdaQueryWrapper = new LambdaQueryWrapper<>();
            flightLambdaQueryWrapper.eq(Flight::getFlightId, flightId);
            //查询订单对应的航班信息
            Flight flight = flightService.getOne(flightLambdaQueryWrapper);
            String departureTime = flight.getDepartureTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //航班起飞时间
            LocalDateTime departure = LocalDateTime.parse(departureTime, formatter);
            //当前时间
            LocalDateTime currentTime = LocalDateTime.now();
            //最晚退票天数
            LocalDateTime latest = currentTime.minusDays(LATEST_CANCEL);
            //如果飞机已经超过起飞时间
            if(departure.isBefore(currentTime)){
                //如果飞机票已经用了
                if(Objects.equals(orders.getIsUse(), IS_USE_TICKET)){
                    return JsonResponse.error("该票已经使用");
                }
                //飞机票没用，但是超过最晚退票时间
                else if(departure.isBefore(latest)){
                    return JsonResponse.error("该票超过退票时间");
                }
            }
            //如果飞机未起飞，回滚座位数量
            if(departure.isAfter(currentTime)){
                String seatType = orders.getSeatType();
                //判断用户该张票的座位类型，如果是经济舱
                int availableSeats;
                if (seatType.equals("0")) {
                    availableSeats = flight.getEconomyClassNum();
                } else {
                    availableSeats = flight.getFirstClassNum();
                }
                String seatTypeKey = seatType.equals("0") ? "economy_class_num" : "first_class_num";
                String seatTypeKeyRedis = seatType.equals("0") ? "economyClassNum" : "firstClassNum";
                //回滚redis航班信息
                String redisKey = FLIGHT_MSG + flightId;
                stringRedisTemplate.opsForHash().put(redisKey, seatTypeKeyRedis, String.valueOf(availableSeats + 1));
                //回滚数据库信息
                flightService.update()
                        .setSql(seatTypeKey + " = " + seatTypeKey + " + 1")
                        .eq("flight_id", flightId)
                        .gt(seatTypeKey, 0)
                        .update();
            }
            //先取消托运订单
            baggageService.update().setSql("status = 0").
                    eq("ticket_id", orderId).update();
            //取消订单
            //修改订单字段
            orders.setCancellationTime(LocalDateTime.now());
            orders.setIsCancelled(1L);
            orderService.update(orders, queryWrapper);
            //将用户消费回滚
            Double amount = orders.getAmount();
            Integer bookingPerson = orders.getBookingPerson();
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getId, bookingPerson);
            //获取订单人
            User user = userService.getOne(userLambdaQueryWrapper);
            //回滚用户消费
            user.setTotalExpenses(user.getTotalExpenses() - amount);
            userService.update(user, userLambdaQueryWrapper);
            return JsonResponse.success("退票成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 升舱
     * @param orders
     * @return
     */
    @Transactional
    @PostMapping("/upgrade")
    public JsonResponse upgrade(@RequestBody Orders orders){
        //先将原先的升舱字段改为1
        boolean update = orderService.update().setSql("is_upgrade = 1").
                eq("order_id", orders.getOrderId()).update();
        if(!update){
            return JsonResponse.error("网络异常");
        }
        //更改用户消费

        //生成新的升舱机票
        Long orderId = orders.getOrderId();
        Snowflake snowflakeId = IdUtil.getSnowflake(RandomUtil.randomInt(0, 31));
        long id = snowflakeId.nextId();
        orders.setOrderId(id);
        orders.setIsUpgradeOrder(1);
        boolean save = orderService.save(orders);
        if(!save){
            return JsonResponse.success("网络异常");
        }
        baggageService.update().setSql("ticket_id=" + id).
                eq("ticket_id", orderId).update();
        boolean setUpdatedOrderId = orderService.update().setSql("upgrade_order_id=" + id).
                eq("order_id", orderId).update();
        if(!setUpdatedOrderId){
            return JsonResponse.error("网络异常");
        }
        return JsonResponse.success("升舱成功");
    }

    /**
     * 查看订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/getOrder/{orderId}")
    public JsonResponse getOrderAllMsg(@PathVariable Long orderId){
        String redisKey = ORDER_MSG + orderId;
        Map<Object, Object> orderMap = stringRedisTemplate.opsForHash().entries(redisKey);
        if(!orderMap.isEmpty()){
            Orders orders = BeanUtil.fillBeanWithMap(orderMap, new Orders(), false);
            return JsonResponse.success(orders);
        }
        else{
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Orders::getOrderId, orderId);
            List<Orders> list = orderService.list(queryWrapper);
            if(list == null || list.size() == 0){
                return JsonResponse.error("网络异常");
            }
            populateOrder(list);
            Orders order = list.get(0);
            //存到redis中
            Map<String, Object> orderMapById = BeanUtil.beanToMap(
                    order, new HashMap<>(), CopyOptions.create().
                            setIgnoreNullValue(true).
                            setFieldValueEditor((fieldName, fieldValue) -> {
                                if (fieldValue == null) {
                                    fieldValue = "";
                                } else {
                                    fieldValue = fieldValue + "";
                                }
                                return fieldValue;
                            }));
            stringRedisTemplate.opsForHash().putAll(redisKey, orderMapById);
            return JsonResponse.success(order);
        }
    }



}
