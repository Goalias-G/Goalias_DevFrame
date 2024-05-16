package com.aigc.vrmetal.pojo.vo;

import com.aigc.vrmetal.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private long total;
    private List<User> userList;
}
