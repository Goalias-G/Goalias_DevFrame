package com.dev.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dev.model.pojo.dto.PageQueryDto;
import com.dev.model.pojo.dto.UserDto;
import com.dev.model.pojo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    IPage<User> userPageQuery(IPage<User> page, @Param("pageQueryDto") PageQueryDto pageQueryDto);

    void change(@Param("userDto") UserDto userDto);

}
