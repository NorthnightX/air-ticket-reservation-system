package com.atrs.airticketreservationsystem.utils;

import com.atrs.airticketreservationsystem.entity.AdminDTO;

public class UserHolder {
    private static final ThreadLocal<AdminDTO> tl = new ThreadLocal<>();

    public static void saveUser(AdminDTO user){
        tl.set(user);
    }

    public static AdminDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
