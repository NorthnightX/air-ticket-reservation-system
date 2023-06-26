package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.common.ImageVerificationCode;
import com.atrs.airticketreservationsystem.entity.Admin;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginForm;
import com.atrs.airticketreservationsystem.mapper.AdminMapper;
import com.atrs.airticketreservationsystem.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

import static com.atrs.airticketreservationsystem.common.RedisConstants.LOGIN_VERIFICATION_KEY;
import static com.atrs.airticketreservationsystem.common.RedisConstants.LOGIN_VERIFICATION_TTL;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public JsonResponse login(LoginForm loginForm) {
        return null;
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
