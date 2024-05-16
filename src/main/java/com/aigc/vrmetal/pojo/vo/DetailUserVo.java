package com.aigc.vrmetal.pojo.vo;

import com.aigc.vrmetal.pojo.entity.Drug;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class DetailUserVo implements Serializable {
    private Integer id;
    private String username;
    private String sex;
    private Integer age;
    private String phoneNumber;
    private String diagnosis;
    private LocalDateTime clinicTime;
    private List<Drug> drugs;

}
