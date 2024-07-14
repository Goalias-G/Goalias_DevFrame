package com.dev.model.controller;


import com.dev.model.context.UserContext;
import com.dev.model.pojo.dto.PageQueryDto;
import com.dev.model.pojo.dto.PhoneLoginDto;
import com.dev.model.pojo.dto.UserDto;
import com.dev.model.pojo.entity.User;
import com.dev.model.pojo.vo.LoginVO;
import com.dev.model.pojo.vo.Result;
import com.dev.model.pojo.vo.UserVo;
import com.dev.model.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gws
 * @since 2024-03-10
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class UserController {
    @Resource
    private IUserService userService;
    @ApiOperation("验证码接口")
    @PostMapping("getCode")
//    @GoaliasHot(grade= FlowGradeEnum.FLOW_GRADE_THREAD,count = 1000,duration = 1000)
    public Result getCode(@RequestBody String phone){
        userService.sendCode(phone);
        return Result.success();
    }
    @ApiOperation("登录接口")
    @PostMapping("login")
    public Result<LoginVO> login(@RequestBody @ApiParam(name = "phoneLoginDto",value = "账号密码",required = true) PhoneLoginDto phoneLoginDto){
        LoginVO loginVO = userService.login(phoneLoginDto);
        if (loginVO == null) {
            return Result.error("账号或密码错误");
        }
        return Result.success(loginVO);
    }
    @ApiOperation("分页获取用户信息")
    @GetMapping("getAll")
    public Result<UserVo> getAll(PageQueryDto pageQueryDto){
        UserVo userVo=userService.userPageQuery(pageQueryDto);
        return Result.success(userVo);
    }
    @ApiOperation("添加用户")
    @GetMapping("add")
    public Result addUser(User user){
        user.setRegisterTime(LocalDateTime.now());
        userService.save(user);
        return Result.success();
    }
    @ApiOperation("个人信息")
    @GetMapping("getOne")
    public Result getUser(){
        Long userId = UserContext.getCurrentId();
        User userById = userService.getById(userId);
        return Result.success(userById);
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询回显用户")
    public Result<User> getById(@PathVariable Long id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @ApiOperation("修改信息")
    @PostMapping("change/{id}")
    public Result changeUser(@RequestBody UserDto userDto, @PathVariable String id){
        userDto.setId(Integer.parseInt(id));
        userService.change(userDto);
        return Result.success();
    }
    @ApiOperation("修改信息")
    @PostMapping("update")
    public Result updateUser(@RequestBody UserDto userDto){
        Long userId = UserContext.getCurrentId();
        userDto.setId(userId.intValue());
        userService.change(userDto);
        return Result.success();
    }
    @ApiOperation("删除用户")
    @PostMapping("deleteUser/{id}")
    public Result deleteOne(@PathVariable String id){
        userService.removeById(Integer.parseInt(id));
        return Result.success();
    }




}
