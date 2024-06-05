package com.dev.model.pojo.dto;


import lombok.Data;

import java.io.Serializable;
@Data
public class UserDto implements Serializable {
    private Integer id;
    private String username;

    private String password;

    private String phoneNumber;

    private Integer age;

    private String portrait;
}
