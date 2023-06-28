package com.atrs.airticketreservationsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Member {
  @TableId(type = IdType.AUTO)
  private Long memberId;
  private Double requiredExpenses;
  private Double discountRate;
  private LocalDateTime publishTime;
  private LocalDateTime modifyTime;
  private String creator;
  private String modifier;


}
