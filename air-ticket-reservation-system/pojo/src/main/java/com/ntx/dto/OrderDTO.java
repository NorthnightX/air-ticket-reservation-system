package com.ntx.dto;


import com.ntx.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    List<Orders> orders;
}
