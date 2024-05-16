package com.aigc.vrmetal.service;

import com.aigc.vrmetal.pojo.dto.PageQueryDto;
import com.aigc.vrmetal.pojo.dto.PhoneLoginDto;
import com.aigc.vrmetal.pojo.entity.Doctor;
import com.aigc.vrmetal.pojo.vo.DoctorVo;
import com.aigc.vrmetal.pojo.vo.LoginVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gws
 * @since 2024-03-14
 */
public interface IDoctorService extends IService<Doctor> {

    LoginVO login(PhoneLoginDto phoneLoginDto);

    DoctorVo userPageQuery(PageQueryDto pageQueryDto);

    void change(Doctor doctorDto);
}
