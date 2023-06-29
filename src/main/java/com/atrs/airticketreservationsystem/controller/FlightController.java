package com.atrs.airticketreservationsystem.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.AircraftInformationService;
import com.atrs.airticketreservationsystem.service.AirportService;
import com.atrs.airticketreservationsystem.service.FlightService;
import com.atrs.airticketreservationsystem.service.RouteService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import static com.atrs.airticketreservationsystem.common.RedisConstants.FLIGHT_MSG;

@RestController
@RequestMapping("/flight")
public class FlightController {
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

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @param flight
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                             Flight flight) {
        Page<Flight> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Flight> queryWrapper = new LambdaQueryWrapper<>();

        // 构建航班查询条件
        if (flight.getAircraftCode() != null && !flight.getAircraftCode().isEmpty()) {
            LambdaQueryWrapper<AircraftInformation> aircraftInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            aircraftInformationLambdaQueryWrapper.like(AircraftInformation::getAircraftCode, flight.getAircraftCode());
            List<AircraftInformation> aircraft = aircraftInformationService.list(aircraftInformationLambdaQueryWrapper);
            List<Long> airportIds = aircraft.stream().map(AircraftInformation::getId).toList();
            if(airportIds.size() == 0){
                return JsonResponse.error("没有该航班");
            }
            queryWrapper.in(Flight::getAircraftId, airportIds);
        }
        List<Route> routeList = getRoutes(flight);
        if (!routeList.isEmpty()) {
            List<Long> routeIds = routeList.stream().map(Route::getId).collect(Collectors.toList());
            queryWrapper.in(Flight::getRouteId, routeIds);
        } else{
            return JsonResponse.error("该城市没有航班");
        }
        Page<Flight> flightPage = flightService.page(page, queryWrapper);
        List<Flight> flightList = flightPage.getRecords();
        
        populateFlightData(flightList);
        page.setRecords(flightList);
        page.setTotal(flightPage.getTotal());
        return JsonResponse.success(page);
    }

    /**
     * 获取航线
     * @param flight
     * @return
     */
    private List<Route> getRoutes(Flight flight) {
        LambdaQueryWrapper<Airport> departureWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Airport> destinationWrapper = new LambdaQueryWrapper<>();

        // 构建出发地和目的地查询条件
        if (flight.getDepartureCity() != null && !flight.getDepartureCity().isEmpty()) {
            departureWrapper.eq(Airport::getCity, flight.getDepartureCity());
        }
        if (flight.getDestinationCity() != null && !flight.getDestinationCity().isEmpty()) {
            destinationWrapper.eq(Airport::getCity, flight.getDestinationCity());
        }

        List<Airport> departureAirports = airportService.list(departureWrapper);
        List<Airport> destinationAirports = airportService.list(destinationWrapper);

        List<Long> departureAirportIds = departureAirports.stream().map(Airport::getId).collect(Collectors.toList());
        List<Long> destinationAirportIds = destinationAirports.stream().map(Airport::getId).collect(Collectors.toList());

        LambdaQueryWrapper<Route> routeWrapper = new LambdaQueryWrapper<>();
        routeWrapper.in(Route::getDepartureAirportId, departureAirportIds);
        routeWrapper.in(Route::getDestinationAirportId, destinationAirportIds);
        return routeService.list(routeWrapper);
    }

    /**
     * 填充航班数据
     * @param flightList
     */
    private void populateFlightData(List<Flight> flightList) {
        Set<Long> routeIds = flightList.stream().map(Flight::getRouteId).collect(Collectors.toSet());
        Set<Long> aircraftIds = flightList.stream().map(Flight::getAircraftId).collect(Collectors.toSet());

        Map<Long, Route> routeMap = routeService.listByIds(routeIds).stream()
                .collect(Collectors.toMap(Route::getId, Function.identity()));
        Map<Long, AircraftInformation> aircraftMap = aircraftInformationService.listByIds(aircraftIds).stream()
                .collect(Collectors.toMap(AircraftInformation::getId, Function.identity()));

        for (Flight flight : flightList) {
            Route route = routeMap.get(flight.getRouteId());
            if (route != null) {
                Airport departureAirport = airportService.getById(route.getDepartureAirportId());
                Airport destinationAirport = airportService.getById(route.getDestinationAirportId());
                if (departureAirport != null && destinationAirport != null) {
                    flight.setDepartureAirportId(departureAirport.getId());
                    flight.setDestinationAirportId(destinationAirport.getId());
                    flight.setDestinationAirport(destinationAirport.getAirportName());
                    flight.setDestinationCity(destinationAirport.getCity());
                    flight.setDepartureAirport(departureAirport.getAirportName());
                    flight.setDepartureCity(departureAirport.getCity());
                }
            }
            AircraftInformation aircraftInformation = aircraftMap.get(flight.getAircraftId());
            if (aircraftInformation != null) {
                flight.setAircraftCode(aircraftInformation.getAircraftCode());
            }
        }
    }
}
