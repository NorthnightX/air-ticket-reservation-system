package com.atrs.airticketreservationsystem.controller;

import cn.hutool.crypto.digest.MD5;

import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginFormData;
import com.atrs.airticketreservationsystem.entity.User;
import com.atrs.airticketreservationsystem.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static com.atrs.airticketreservationsystem.common.SystemConstants.DEFAULT_PASSWORD;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize,
                             User user){
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(user.getUsername().length() > 0, User::getUsername, user.getUsername());
        queryWrapper.eq(user.getAccountStatus().length() > 0 , User::getAccountStatus, user.getAccountStatus());
        queryWrapper.orderByDesc(User::getVipStatus);
        Page<User> agentPage = userService.page(page, queryWrapper);
        return JsonResponse.success(agentPage);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping("/updateUser")
    public JsonResponse updateUser(@RequestBody User user){
        boolean updated = userService.updateById(user);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 修改用户状态
     * @param user
     * @return
     */
    @PutMapping("/updateUserStatus")
    public JsonResponse updateUserStatus(@RequestBody User user){
        long id = user.getId();
        User userById = userService.getById(id);
        userById.setAccountStatus(user.getAccountStatus());
        boolean updated = userService.updateById(userById);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @PutMapping("/updatePassword")
    public JsonResponse updatePassword(@RequestParam long id){
        String md5Password = MD5.create().digestHex(DEFAULT_PASSWORD);
        User user = userService.getById(id);
        user.setPassword(md5Password);
        boolean updated = userService.updateById(user);
        if(!updated){
            return JsonResponse.error("重置失败");
        }
        return JsonResponse.success("密码重置成功");
    }

    /**
     * 列表删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id){
        boolean removeById = userService.removeByIds(id);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 用户登录
     * @param loginFormData
     * @return
     */
    @PostMapping("/login")
    public JsonResponse loginUser(@RequestBody LoginFormData loginFormData){
        return userService.login(loginFormData);
    }
}
