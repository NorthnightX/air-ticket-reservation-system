package com.atrs.airticketreservationsystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

  private Long orderId;
  private Long flightId;
  private String seatType;
  private String bookingPerson;
  private String passenger;
  private Long isCheckedBaggage;
  private Double amount;
  private LocalDateTime orderTime;
  private Long isCancelled;
  private LocalDateTime cancellationTime;
  private Long isUpgrade;
  private Long upgradeOrderId;


}
