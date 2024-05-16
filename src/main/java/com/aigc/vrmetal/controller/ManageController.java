package com.aigc.vrmetal.controller;


import com.aigc.vrmetal.pojo.dto.ManagerLoginDto;
import com.aigc.vrmetal.pojo.dto.ResetPasswordDto;
import com.aigc.vrmetal.pojo.entity.Doctor;
import com.aigc.vrmetal.pojo.entity.User;
import com.aigc.vrmetal.pojo.vo.LoginVO;
import com.aigc.vrmetal.pojo.vo.Result;
import com.aigc.vrmetal.service.IManageService;
import com.aigc.vrmetal.service.IUserService;
import com.aigc.vrmetal.service.IDoctorService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gws
 * @since 2024-03-10
 */
@RestController
@RequestMapping("/manage")
@Api(tags = "管理员功能接口")
public class ManageController {
    @Resource
    private IManageService manageService;
    @Resource
    private IUserService userService;
    @Resource
    private IDoctorService doctorService;
    @PostMapping("login")
    @ApiOperation("登录")
    public Result<LoginVO> login(@RequestBody ManagerLoginDto managerLoginDto){
        LoginVO loginVO=manageService.login(managerLoginDto);
        return Result.success(loginVO);
    }
    @ApiOperation("重置用户密码")
    @PostMapping("resetUser")
    public Result resetPasswordUser(@RequestBody ResetPasswordDto resetDto){
        UpdateWrapper<User> updateWrapper=new UpdateWrapper();
        updateWrapper.lambda().eq(User::getUsername,resetDto.getName())
                .eq(User::getPhoneNumber,resetDto.getPhone())
                .set(User::getPassword,"123456");
        if (userService.update(updateWrapper)) {
            return Result.success();
        }
        return Result.error("用户密码修改失败");

    }
    @ApiOperation("重置医生密码")
    @PostMapping("resetDoctor")
    public Result resetPasswordDoctor(@RequestBody ResetPasswordDto resetDto){
        UpdateWrapper<Doctor> updateWrapper=new UpdateWrapper();
        updateWrapper.lambda().eq(Doctor::getName,resetDto.getName())
                .eq(Doctor::getPhoneNum,resetDto.getPhone())
                .set(Doctor::getPassword,"123456");
        if (doctorService.update(updateWrapper)) {
            return Result.success();
        }
        return Result.error("医生密码修改失败");

    }

}
