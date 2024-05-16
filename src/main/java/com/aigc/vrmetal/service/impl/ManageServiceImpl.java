package com.aigc.vrmetal.service.impl;

import com.aigc.vrmetal.pojo.dto.ManagerLoginDto;
import com.aigc.vrmetal.pojo.entity.Manage;
import com.aigc.vrmetal.mapper.ManageMapper;
import com.aigc.vrmetal.pojo.vo.LoginVO;
import com.aigc.vrmetal.properties.JwtProperties;
import com.aigc.vrmetal.service.IManageService;
import com.aigc.vrmetal.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gws
 * @since 2024-03-10
 */
@Service
@Primary
public class ManageServiceImpl extends ServiceImpl<ManageMapper, Manage> implements IManageService {
    @Resource
    private ManageMapper manageMapper;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public LoginVO login(ManagerLoginDto managerLoginDto) {
        String manageName = managerLoginDto.getManageName();
        String managePassword = managerLoginDto.getManagePassword();
        QueryWrapper<Manage> queryWrapper=new QueryWrapper();
        queryWrapper.eq("manage_name",manageName);
        Manage manager = manageMapper.selectOne(queryWrapper);
        if (manager == null || !manager.getManagePassword().equals(managePassword)) {
            return null;
        }
        Map<String,Object> claims=new HashMap<>(2);
        claims.put("manangerId",manager.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
        LoginVO loginVO = LoginVO.builder()
                .id(manager.getId().longValue())
                .account(manager.getManageName())
                .token(jwt)
                .build();
        return loginVO;
    }
}
