package com.dev.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel(description = "用户修改字段类")
public class UserDto implements Serializable {
    private Integer id;
    @ApiModelProperty(name = "用户名")
    private String username;

    private String password;

    private String phoneNumber;

    private Integer age;

    private String portrait;
}
