package com.aigc.vrmetal.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ManagerLoginDto implements Serializable{
    private String manageName;

    private String managePassword;

    }

