package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
            }
        }
    }

    @PutMapping("/updateOrder")
    public JsonResponse updateOrder(@RequestBody Orders orders) throws ParseException {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getOrderId, orders.getOrderId());
        boolean updated = orderService.update(orders, lambdaQueryWrapper);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    @Transactional
    @PostMapping("/delete/{orderId}")
    public JsonResponse delete(@PathVariable Long orderId){
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Baggage> baggageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getOrderId, orderId);
        baggageLambdaQueryWrapper.eq(Baggage::getTicketId, orderId);
        baggageService.remove(baggageLambdaQueryWrapper);
        boolean delete = orderService.remove(ordersLambdaQueryWrapper);
        if(!delete){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

}
