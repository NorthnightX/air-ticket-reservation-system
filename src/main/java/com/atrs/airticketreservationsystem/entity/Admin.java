package com.atrs.airticketreservationsystem.entity;

import lombok.Data;

@Data
public class Admin {

  private Long id;
  private String username;
  private String account;
  private String password;
  private String avatar;
  private String administratorType;
  private String status;



}
