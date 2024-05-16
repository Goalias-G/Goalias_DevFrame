package com.aigc.vrmetal.pojo.vo;

import com.aigc.vrmetal.pojo.entity.Doctor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class DoctorVo implements Serializable {
    private long total;
    private List<Doctor> doctorList;
}
