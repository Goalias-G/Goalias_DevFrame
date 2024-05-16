package com.aigc.vrmetal.service;

import com.aigc.vrmetal.pojo.dto.PageQueryDto;
import com.aigc.vrmetal.pojo.dto.PhoneLoginDto;
import com.aigc.vrmetal.pojo.dto.UserDto;
import com.aigc.vrmetal.pojo.entity.User;
import com.aigc.vrmetal.pojo.vo.LoginVO;
import com.aigc.vrmetal.pojo.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gws
 * @since 2024-03-10
 */
public interface IUserService extends IService<User> {

    LoginVO login(PhoneLoginDto phoneLoginDto);

    UserVo userPageQuery(PageQueryDto pageQueryDto);

    void change(UserDto userDto);

    void sendCode(String account);
}
