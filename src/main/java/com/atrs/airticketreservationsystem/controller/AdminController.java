package com.atrs.airticketreservationsystem.controller;


import com.atrs.airticketreservationsystem.entity.Administrator;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginFormData;
import com.atrs.airticketreservationsystem.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    public AdminService adminService;


    @PostMapping("/code")
    public JsonResponse code() throws IOException {
        return adminService.code();
    }
    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginFormData loginForm){
        return adminService.login(loginForm);
    }

    @DeleteMapping("/delete")
    public JsonResponse delete(){
        return JsonResponse.error("!");
    }

    @GetMapping("/page")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "6")Integer pageSize,
                             Administrator admin){
        return JsonResponse.error("1");
    }
}
