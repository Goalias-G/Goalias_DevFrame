package com.aigc.vrmetal.service;

import com.aigc.vrmetal.pojo.dto.ManagerLoginDto;
import com.aigc.vrmetal.pojo.entity.Manage;
import com.aigc.vrmetal.pojo.vo.LoginVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gws
 * @since 2024-03-10
 */
public interface IManageService extends IService<Manage> {

    LoginVO login(ManagerLoginDto managerLoginDto);
}
