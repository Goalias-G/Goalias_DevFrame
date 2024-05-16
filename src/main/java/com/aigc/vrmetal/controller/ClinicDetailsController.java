package com.aigc.vrmetal.controller;


import com.aigc.vrmetal.pojo.entity.ClinicDetails;
import com.aigc.vrmetal.pojo.vo.Result;
import com.aigc.vrmetal.service.IClinicDetailsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gws
 * @since 2024-03-14
 */
@RestController
@RequestMapping("/clinic-details")
public class ClinicDetailsController {

    @Resource
    private IClinicDetailsService clinicDetailsService;
    @ApiOperation("新增诊断")
    @PostMapping("add")
    public Result add(@RequestBody ClinicDetails clinicDetails){
        clinicDetails.setClinicTime(LocalDateTime.now());
        clinicDetailsService.save(clinicDetails);
        return Result.success();
    }
    @ApiOperation("删除诊断")
    @PostMapping("delete/{id}")
    public Result delete(@PathVariable String id){
        clinicDetailsService.removeById(Integer.parseInt(id));
        return Result.success();
    }

}
