package com.atrs.airticketreservationsystem.controller;


import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.AircraftInformationService;
import com.atrs.airticketreservationsystem.service.AirportService;
import com.atrs.airticketreservationsystem.service.FlightService;
import com.atrs.airticketreservationsystem.service.RouteService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import static com.atrs.airticketreservationsystem.common.RedisConstants.FLIGHT_MSG;
import static com.atrs.airticketreservationsystem.common.SystemConstants.*;

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
        //根据时间进行查询
        if(flight.getDepartureTime() != null && flight.getDepartureTime().length() > 0){
            queryWrapper.like(Flight::getDepartureTime, flight.getDepartureTime());
        }
        //如果是用户，只展示正常状态和推迟状态的飞机
        Long vipStatus = UserHolder.getUser().getVipStatus();
        System.out.println(UserHolder.getUser());
        if(!Objects.equals(vipStatus, ADMIN_VIP_STATUS)){
            List<Integer> statusList = new ArrayList<>(Arrays.asList(NOT_FLY, DELAY));
            queryWrapper.in(Flight::getStatus, statusList);
        }
        Page<Flight> flightPage = flightService.page(page, queryWrapper);
        List<Flight> flightList = flightPage.getRecords();

        if(flightList.size() == 0){
            return JsonResponse.error("没有符合条件的航班");
        }
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

    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id){
        boolean removeById = flightService.removeByIds(id);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }


    @PostMapping("/addFlight")
    public JsonResponse addFlight(@RequestBody Flight flight) throws ParseException {
        String departureTime = flight.getDepartureTime();
        String arrivalTime = flight.getArrivalTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date departure = sdf.parse(departureTime);
        Date arrival = sdf.parse(arrivalTime);
        if(arrival.before(departure)){
            return JsonResponse.error("到达时间不能早于出发时间");
        }
        flight.setStatus(DEFAULT_STATUS_FLIGHT);
        flight.setModifyTime(LocalDateTime.now());
        flight.setPublishTime(LocalDateTime.now());
        flight.setCreator(UserHolder.getUser().getUsername());
        flight.setModifier(UserHolder.getUser().getUsername());
        Long departureAirportId = flight.getDepartureAirportId();
        Long destinationAirportId = flight.getDestinationAirportId();
        LambdaQueryWrapper<Route> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Route::getDepartureAirportId, departureAirportId);
        queryWrapper.eq(Route::getDestinationAirportId, destinationAirportId);
        Route route = routeService.getOne(queryWrapper);
        flight.setRouteId(route.getId());
        boolean save = flightService.save(flight);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }


    @PutMapping("/updateFlight")
    public JsonResponse updateFlight(@RequestBody Flight flight) throws ParseException {
        String departureTime = flight.getDepartureTime();
        String arrivalTime = flight.getArrivalTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date departure = sdf.parse(departureTime);
        Date arrival = sdf.parse(arrivalTime);
        if(arrival.before(departure)){
            return JsonResponse.error("到达时间不能早于出发时间");
        }
        flight.setModifyTime(LocalDateTime.now());
        flight.setModifier(UserHolder.getUser().getUsername());
        LambdaQueryWrapper<Route> queryWrapper = new LambdaQueryWrapper<>();
        Long departureAirportId = flight.getDepartureAirportId();
        Long destinationAirportId = flight.getDestinationAirportId();
        queryWrapper.eq(Route::getDepartureAirportId, departureAirportId);
        queryWrapper.eq(Route::getDestinationAirportId, destinationAirportId);
        Route route = routeService.getOne(queryWrapper);
        flight.setRouteId(route.getId());
        boolean updated = flightService.updateById(flight);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }
}
