package com.aigc.vrmetal.mapper;

import com.aigc.vrmetal.pojo.dto.PageQueryDto;
import com.aigc.vrmetal.pojo.dto.UserDto;
import com.aigc.vrmetal.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gws
 * @since 2024-03-15
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> userPageQuery(IPage<User> page, @Param("pageQueryDto") PageQueryDto pageQueryDto);

    void change(@Param("userDto") UserDto userDto);

}
