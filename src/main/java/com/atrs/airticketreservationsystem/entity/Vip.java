package com.atrs.airticketreservationsystem.entity;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Vip {

  private Long id;
  private Long level;
  private Double requiredSpending;
  private Double discountRate;
  private LocalDateTime publishTime;
  private LocalDateTime modifyTime;
  private String creator;
  private String modifier;



}
