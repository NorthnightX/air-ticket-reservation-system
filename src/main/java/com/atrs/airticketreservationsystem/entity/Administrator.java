package com.atrs.airticketreservationsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("administrator")
@Data
public class Administrator {

  private Long id;
  private String username;
  private String account;
  private String password;
  private String avatar;
  private String administratorType;
  private String status;



}
