package com.ntx.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ntx.entity.Flight;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlightMapper extends BaseMapper<Flight> {
}
