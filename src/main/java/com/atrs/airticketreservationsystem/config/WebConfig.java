package com.atrs.airticketreservationsystem.config;


import com.atrs.airticketreservationsystem.utils.LoginInterceptor;
import com.atrs.airticketreservationsystem.utils.RefreshTokenInterception;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);

    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/admin/code",
                        "/admin/login"
                ).order(1);
        registry.addInterceptor(new RefreshTokenInterception(stringRedisTemplate)).addPathPatterns(
                "/**"
        ).order(0);
    }

}

