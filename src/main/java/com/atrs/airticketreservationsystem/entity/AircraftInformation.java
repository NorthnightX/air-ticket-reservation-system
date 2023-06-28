package com.atrs.airticketreservationsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AircraftInformation {
  @TableId(type = IdType.AUTO)
  private Long id;
  private Long modelId;
  private String aircraftCode;
  private LocalDateTime purchaseDate;
  private Long serviceYears;
  private LocalDateTime lastMaintenanceDate;
  private LocalDateTime publishTime;
  private LocalDateTime modifyTime;
  private String creator;
  private String modifier;




}
