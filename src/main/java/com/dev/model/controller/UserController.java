package com.dev.model.controller;


import com.dev.model.context.UserContext;
import com.dev.model.pojo.dto.PageQueryDto;
import com.dev.model.pojo.dto.PhoneLoginDto;
import com.dev.model.pojo.dto.UserDto;
import com.dev.model.pojo.entity.User;
import com.dev.model.pojo.vo.LoginVO;
import com.dev.model.pojo.Result;
import com.dev.model.pojo.vo.UserVo;
import com.dev.model.service.IUserService;

import com.tool.goalias.annotation.GoaliasFallback;
import com.tool.goalias.annotation.GoaliasHot;
import com.tool.goalias.enums.FlowGradeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @PostMapping("getCode")
    @GoaliasFallback(grade = FlowGradeEnum.FLOW_GRADE_QPS,count = 1000)//QPS为1000则降级
    public Result getCode(@RequestBody String phone){
        userService.sendCode(phone);
        return Result.success();
    }
    public Result getCodeFallback(@RequestBody String phone){
        return Result.success("请求频繁,请稍后再试");
    }

    @PostMapping("login")
    public Result login(@RequestBody PhoneLoginDto phoneLoginDto){
        LoginVO loginVO = userService.login(phoneLoginDto);
        if (loginVO == null) {
            return Result.error("账号或密码错误");
        }
        return Result.success(loginVO);
    }
    @GetMapping("getAll")
    @GoaliasHot(grade = FlowGradeEnum.FLOW_GRADE_THREAD,count = 1000,duration = 1500)//1.5s内有一千个线程访问则限流
    public Result getAll(PageQueryDto pageQueryDto){
        UserVo userVo=userService.userPageQuery(pageQueryDto);
        return Result.success(userVo);
    }
    @GetMapping("add")
    public Result addUser(User user){
//        user.setRegisterTime(LocalDateTime.now());
        userService.save(user);
        return Result.success();
    }
    @GetMapping("getOne")
    public Result getUser(HttpServletResponse response){
        response.setHeader("Cache-Control","max-age=200");//强制缓存
        Long userId = UserContext.getCurrentId();
        User userById = userService.getById(userId);
        return Result.success(userById);
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PostMapping("change/{id}")
    public Result changeUser(@RequestBody UserDto userDto, @PathVariable String id){
        userDto.setId(Integer.parseInt(id));
        userService.change(userDto);
        return Result.success();
    }
    @PostMapping("update")
    public Result updateUser(@RequestBody UserDto userDto){
        Long userId = UserContext.getCurrentId();
        userDto.setId(userId.intValue());
        userService.change(userDto);
        return Result.success();
    }
    @PostMapping("delete/{id}")
    public Result deleteOne(@PathVariable String id){
        userService.removeById(Integer.parseInt(id));
        return Result.success();
    }




}
