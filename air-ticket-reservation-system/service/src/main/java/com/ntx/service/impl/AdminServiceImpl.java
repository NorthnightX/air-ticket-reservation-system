package com.ntx.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.crypto.digest.MD5;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ntx.dto.UserDTO;
import com.ntx.entity.Administrator;
import com.ntx.entity.ImageVerificationCode;
import com.ntx.entity.JsonResponse;
import com.ntx.entity.LoginFormData;
import com.ntx.mapper.AdminMapper;
import com.ntx.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.ntx.constants.RedisConstants.*;
import static com.ntx.constants.SystemConstants.ADMIN_VIP_STATUS;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Administrator> implements AdminService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public JsonResponse login(LoginFormData loginForm) {
        String password = loginForm.getPassword();
        String account = loginForm.getAccount();
        String code = loginForm.getCode();
        String loginCode = stringRedisTemplate.opsForValue().get(loginForm.getRedisKey());
        if(loginCode == null || loginCode.length() == 0){
            return JsonResponse.error("验证码过期");
        }
        if(!code.equalsIgnoreCase(loginCode)){
            return JsonResponse.error("验证码错误");
        }
        LambdaQueryWrapper<Administrator> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Administrator::getAccount,account);
        Administrator admin = this.getOne(queryWrapper);
        if(admin == null){
            return JsonResponse.error("用户未注册");
        }
        String md5Password = MD5.create().digestHex(password);
        if(!md5Password.equals(admin.getPassword())){
            return JsonResponse.error("密码错误");
        }
        if(admin.getStatus().equals("0")){
            return JsonResponse.error("用户封禁");
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(admin, userDTO);
        userDTO.setVipStatus(ADMIN_VIP_STATUS);
        String token = UUID.randomUUID().toString();
        Map<String, Object> adminMap = BeanUtil.beanToMap(
                userDTO, new HashMap<>(), CopyOptions.create().
                        setIgnoreNullValue(true).
                        setFieldValueEditor((fieldName, fieldValue) -> {
                            if (fieldValue == null) {
                                fieldValue = "0";
                            } else {
                                fieldValue = fieldValue + "";
                            }
                            return fieldValue;
                        }));
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, adminMap);
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return JsonResponse.success(token);
    }

    @Override
    public JsonResponse code() throws IOException {
        Map<String, String> map = new HashMap<>();
        ImageVerificationCode imageVerificationCode = new ImageVerificationCode();
        BufferedImage image = imageVerificationCode.getImage();
        String text = imageVerificationCode.getText();
        String str = UUID.randomUUID().toString().replace("-", "");
        String redisKey = LOGIN_VERIFICATION_KEY + str;
        stringRedisTemplate.opsForValue().set(redisKey, text, LOGIN_VERIFICATION_TTL, TimeUnit.MINUTES);
        //向网页传输验证码图片
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", outputStream);
        byte[] byteArray = outputStream.toByteArray();
        String encode = Base64.getEncoder().encodeToString(byteArray);
        String prefix = "data:image/jpeg;base64,";
        String baseStr = prefix + encode;
        map.put("redisKey", redisKey);
        map.put("base64Str", baseStr);
        return JsonResponse.success(map);
    }
}
