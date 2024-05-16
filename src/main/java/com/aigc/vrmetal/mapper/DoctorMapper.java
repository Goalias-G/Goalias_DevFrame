package com.aigc.vrmetal.mapper;

import com.aigc.vrmetal.pojo.entity.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gws
 * @since 2024-03-14
 */
public interface DoctorMapper extends BaseMapper<Doctor> {
    void reChange(@Param(value = "doctorDto") Doctor doctorDto);
}
