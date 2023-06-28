package com.atrs.airticketreservationsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Flight {
  @TableId(type = IdType.AUTO)
  private Long flightId;
  private Long routeId;
  private Long aircraftId;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private Double economyClassPrice;
  private Double firstClassPrice;
  private java.sql.Timestamp publishTime;
  private java.sql.Timestamp modifyTime;
  private String creator;
  private String modifier;



}
