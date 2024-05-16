package com.aigc.vrmetal.controller;


import com.aigc.vrmetal.pojo.entity.ChatEffect;
import com.aigc.vrmetal.pojo.entity.Doctor;
import com.aigc.vrmetal.pojo.vo.Result;
import com.aigc.vrmetal.service.IChatEffectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gws
 * @since 2024-03-11
 */
@RestController
@RequestMapping("/chat-effect")
@Api(tags = "交谈影响接口")
public class ChatEffectController {
    @Resource
    private IChatEffectService chatEffectService;
    @GetMapping("/{id}")
    @ApiOperation("查询交谈影响")
    public Result<ChatEffect> getById(@PathVariable String id){
        ChatEffect chatEffect = chatEffectService.getById(Integer.parseInt(id));
        return Result.success(chatEffect);
    }
}
