package com.dev.model.pojo.vo;

import com.dev.model.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    private long total;
    private List<User> userList;
}
