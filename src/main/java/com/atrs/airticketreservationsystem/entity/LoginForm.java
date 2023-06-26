package com.atrs.airticketreservationsystem.entity;

import lombok.Data;

@Data
public class LoginForm {
    private String account;
    private String password;
    private String code;
}
