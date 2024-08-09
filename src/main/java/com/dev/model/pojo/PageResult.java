package com.dev.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 分页对象
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {

    private List<T> recordList;

    private Integer rows;

}
