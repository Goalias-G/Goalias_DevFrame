package com.aigc.vrmetal.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aigc.vrmetal.context.BaseContext;
import com.aigc.vrmetal.pojo.dto.*;
import com.aigc.vrmetal.pojo.entity.ClinicDetails;
import com.aigc.vrmetal.pojo.entity.Doctor;
import com.aigc.vrmetal.pojo.entity.Drug;
import com.aigc.vrmetal.pojo.entity.User;
import com.aigc.vrmetal.pojo.vo.*;
import com.aigc.vrmetal.service.IClinicDetailsService;
import com.aigc.vrmetal.service.IDoctorService;
import com.aigc.vrmetal.service.IDrugService;
import com.aigc.vrmetal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RequestMapping("doctor")
@Api(tags = "医生接口")
@RestController
public class DoctorController {
    @Resource
    private IDoctorService doctorService;
    @Resource
    private IClinicDetailsService clinicDetailsService;
    @Resource
    private IDrugService drugService;
    @Resource
    private IUserService userService;
    @ApiOperation("登录接口")
    @PostMapping("login")
    public Result<LoginVO> login(@RequestBody PhoneLoginDto phoneLoginDto){
        LoginVO loginVO = doctorService.login(phoneLoginDto);
        if (loginVO == null) {
            return Result.error("账号或密码错误");
        }
        return Result.success(loginVO);
    }
    @ApiOperation("分页获取医生信息")
    @GetMapping("getAll")
    public Result<DoctorVo> getAll(PageQueryDto pageQueryDto){
        DoctorVo doctorVo=doctorService.userPageQuery(pageQueryDto);
        return Result.success(doctorVo);
    }
    @ApiOperation("添加医生")
    @GetMapping("add")
    public Result addUser(Doctor doctor){
        doctorService.save(doctor);
        return Result.success();
    }
    @ApiOperation("医生个人信息")
    @GetMapping("getOne")
    public Result getUser(){
        Long phone = BaseContext.getCurrentId();
        Doctor doctor = doctorService.getOne(new QueryWrapper<Doctor>().eq("phone_num", phone));
        if (doctor == null) {
            return Result.error(401,"未登录");
        }
        return Result.success(doctor);
    }
    @GetMapping("/{id}")
    @ApiOperation("管理员根据id查询回显医生")
    public Result<Doctor> getById(@PathVariable String id){
        Doctor doctor = doctorService.getById(Integer.parseInt(id));
        return Result.success(doctor);
    }

    @ApiOperation("修改医生信息")
    @PostMapping("change/{id}")
    public Result changeUser(@RequestBody Doctor doctorDto, @PathVariable String id){
        doctorDto.setId(Integer.parseInt(id));
        doctorService.change(doctorDto);
        return Result.success();
    }
    @ApiOperation("管理员删除")
    @PostMapping("deleteUser/{id}")
    public Result deleteOne(@PathVariable String id){
        doctorService.removeById(Integer.parseInt(id));
        return Result.success();
    }
    @ApiOperation("医生查询用户详细信息")
    @PostMapping("detailUser")
    public Result detailUser(){
        List<User> userList = userService.list();
        List<DetailUserVo> detailUserVos = BeanUtil.copyToList(userList, DetailUserVo.class);
        Thread thread0=new Thread(()->{
            for (DetailUserVo detailUserVo : detailUserVos) {
            ClinicDetails clinicDetails = clinicDetailsService.getById(detailUserVo.getId());
            if (clinicDetails!=null){
                detailUserVo.setDiagnosis(clinicDetails.getDiagnosis());
                detailUserVo.setClinicTime(clinicDetails.getClinicTime());
            }
        }});
        thread0.start();
        Thread thread1 = new Thread(()->{
            for (DetailUserVo detailUserVo : detailUserVos) {
            List<Drug> drugs = drugService.list(new QueryWrapper<Drug>().eq("drug_id", detailUserVo.getId()));
            detailUserVo.setDrugs(drugs);
        }});
        thread1.start();
        try {
            thread0.join();
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success(detailUserVos);
    }

}
