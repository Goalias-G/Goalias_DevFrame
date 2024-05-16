package com.aigc.vrmetal.pojo.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class ResetPasswordDto implements Serializable {
    private String name;
    private String phone;
}
