package com.dev.model.pojo.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class PhoneLoginDto implements Serializable {
    private String phoneNum;
    private String password;
}
