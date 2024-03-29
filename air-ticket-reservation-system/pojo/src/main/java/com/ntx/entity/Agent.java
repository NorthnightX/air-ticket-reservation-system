package com.ntx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Agent {

  @TableId(type = IdType.AUTO)
  private Long id;
  private String username;
  private String account;
  private String password;
  private String avatar;
  private String qualificationNumber;
  private Integer level;
  private String status;



}
