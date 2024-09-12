package com.dev.model.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dev.model.mapper.UserMapper;
import com.dev.model.pojo.dto.PageQueryDto;
import com.dev.model.pojo.dto.PhoneLoginDto;
import com.dev.model.pojo.dto.UserDto;
import com.dev.model.pojo.entity.User;
import com.dev.model.pojo.vo.LoginVO;
import com.dev.model.pojo.vo.UserVo;
import com.dev.model.context.properties.JwtProperties;
import com.dev.model.service.IUserService;
import com.dev.model.utils.JwtUtil;
import com.dev.model.utils.SMSUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gws
 * @since 2024-03-10
 */
@Service
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public LoginVO login(PhoneLoginDto phoneLoginDto) {
        String phoneNum = phoneLoginDto.getPhoneNum();
        String password = phoneLoginDto.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phoneNum);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null || !password.equals(user.getPassword())) {
            return null;
        }
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("userId", user.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
        return LoginVO.builder()
                .id(user.getId().longValue())
//                .account(user.getUsername())
                .token(jwt)
                .build();
    }

    @Override
    @Cacheable(cacheNames = "cacheSpace1",keyGenerator = "keyGenerator",cacheManager = "redisCacheManager",unless = "#result==null")
    public UserVo userPageQuery(PageQueryDto pageQueryDto) {
        IPage<User> page = new Page<>(pageQueryDto.getPage(), pageQueryDto.getPageSize());
        IPage<User> userPage = userMapper.userPageQuery(page, pageQueryDto);
        return UserVo.builder()
                .userList(userPage.getRecords())
                .total(userPage.getTotal())
                .build();
    }

    @Override
    @CacheEvict(value = "cacheSpace1")
    public void change(@NotNull UserDto userDto) {
        if (!userDto.getPassword().isEmpty()) {
            applicationEventPublisher.publishEvent(userDto);
        }
        userMapper.change(userDto);
    }

    @Override
    public void sendCode(String phone) {
        String message = null;
        String code = RandomUtil.randomNumbers(6);
        if (!SMSUtils.isMobileNum(phone)) {
            throw new RuntimeException("手机号不合法");
        }
        boolean isSuccess = SMSUtils.sendCode(phone, code);
        if (isSuccess) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("phone_number", phone).set("password", code);//用redis设置过期时间最好
            userMapper.update(updateWrapper);
        }
    }
}
