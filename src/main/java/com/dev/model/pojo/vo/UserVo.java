package com.dev.model.pojo.vo;

import com.dev.model.pojo.entity.User;
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
