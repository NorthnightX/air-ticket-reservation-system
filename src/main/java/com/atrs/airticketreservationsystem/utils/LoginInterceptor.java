package com.atrs.airticketreservationsystem.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        //判断是否要拦截
        if(UserHolder.getUser() == null){
            response.setStatus(401);
            return false;
        }
        //有用户放行
        return true;
    }
}
