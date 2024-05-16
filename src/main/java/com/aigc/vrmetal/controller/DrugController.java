package com.aigc.vrmetal.controller;


import com.aigc.vrmetal.pojo.entity.Drug;
import com.aigc.vrmetal.pojo.vo.Result;
import com.aigc.vrmetal.service.IDrugService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gws
 * @since 2024-03-14
 */
@RestController
@RequestMapping("/drug")
public class DrugController {
    @Resource
    private IDrugService drugService;
    @ApiOperation("医生开药")
    @PostMapping("add")
    public Result add(@RequestBody Drug drug){
        drugService.save(drug);
        return Result.success();
    }
    @ApiOperation("删除开药")
    @PostMapping("delete/{id}")
    public Result delete(@PathVariable String id){
        drugService.removeById(Integer.parseInt(id));
        return Result.success();
    }


}
