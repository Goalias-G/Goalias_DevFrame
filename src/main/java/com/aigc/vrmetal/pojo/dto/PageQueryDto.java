package com.aigc.vrmetal.pojo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PageQueryDto implements Serializable {
    public PageQueryDto() {
        this.page=1;
        this.pageSize=5;
    }

    private int page;

    private int pageSize;
    private String username;
    private String phoneNumber;
}
