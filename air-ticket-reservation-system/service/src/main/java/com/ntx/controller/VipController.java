package com.ntx.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ntx.entity.JsonResponse;
import com.ntx.entity.Vip;
import com.ntx.service.VipService;
import com.ntx.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/vip")
public class VipController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private VipService vipService;

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize)
    {
        Page<Vip> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Vip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Vip::getLevel);
        Page<Vip> vipPage = vipService.page(page, queryWrapper);
        return JsonResponse.success(vipPage);
    }

    /**
     * 获取当前登录用户的vip折扣信息
     * @return
     */
    @GetMapping("/getUserVipDisCountRate")
    public JsonResponse getUserVipDisCountRate() {
        Long vipStatus = UserHolder.getUser().getVipStatus();
        LambdaQueryWrapper<Vip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Vip::getLevel, vipStatus);
        queryWrapper.orderByDesc(Vip::getLevel);
        return JsonResponse.success(vipService.getOne(queryWrapper));
    }

    /**
     * 更新vip信息
     * @param vip
     * @return
     */
    @PutMapping("/updateVip")
    public JsonResponse updateVip(@RequestBody Vip vip){
        vip.setModifyTime(LocalDateTime.now());
        vip.setModifier(UserHolder.getUser().getUsername());
        boolean updated = vipService.updateById(vip);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */

    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id){
        boolean removeById = vipService.removeByIds(id);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 新增
     * @param vip
     * @return
     */
    @PostMapping("/addVip")
    public JsonResponse addVip(@RequestBody Vip vip){
        vip.setModifyTime(LocalDateTime.now());
        vip.setPublishTime(LocalDateTime.now());
        vip.setCreator(UserHolder.getUser().getUsername());
        vip.setModifier(UserHolder.getUser().getUsername());
        boolean save = vipService.save(vip);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }
}
