package com.ntx.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ntx.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
