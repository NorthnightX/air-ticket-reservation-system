package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginForm;
import com.atrs.airticketreservationsystem.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    public AdminService adminService;


    @GetMapping("/code")
    public JsonResponse code() throws IOException {
        return adminService.code();
    }
    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginForm loginForm){
        return adminService.login(loginForm);
    }
}
