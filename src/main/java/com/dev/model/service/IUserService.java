package com.dev.model.service;

import com.dev.model.pojo.dto.PageQueryDto;
import com.dev.model.pojo.dto.PhoneLoginDto;
import com.dev.model.pojo.dto.UserDto;
import com.dev.model.pojo.entity.User;
import com.dev.model.pojo.vo.LoginVO;
import com.dev.model.pojo.vo.UserVo;
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
