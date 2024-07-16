package com.dev.model.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.dev.model.pojo.entity.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserCanalHandleServiceImpl implements CanalHandleService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void createSql(List<CanalEntry.RowData> rowDataList) {

    }

    @Override
    public void selectSql(List<CanalEntry.RowData> rowDataList) {

    }

    @Override
    public void insertSql(List<CanalEntry.RowData> rowDataList) {
        rowDataList.forEach(rowData -> {
            User user = parseRowData(rowData);
            stringRedisTemplate.opsForValue().set("user:"+user.getId(),user.toString());
        });
    }

    @Override
    public void updateSql(List<CanalEntry.RowData> rowDataList) {
        rowDataList.forEach(rowData -> {
            User user = parseRowData(rowData);
            stringRedisTemplate.opsForValue().set("user:"+user.getId(),user.toString());
        });
    }

    @Override
    public void deleteSql(List<CanalEntry.RowData> rowDataList) {

    }

    private User parseRowData(CanalEntry.RowData rowData) {
        User user = new User();
        rowData.getAfterColumnsList().forEach(column -> {
            switch (column.getName()) {
                case "id":
                    user.setId(Integer.parseInt(column.getValue()));break;
                case "sex":
                    user.setSex(column.getValue());break;

                case "username":
                    user.setUsername(column.getValue());break;

                case "password":
                    user.setPassword(column.getValue());break;

                case "phone_number":
                    user.setPhoneNumber(column.getValue());break;

                case "age":
                    user.setAge(Integer.parseInt(column.getValue()));break;

                case "register_time":
                    user.setRegisterTime(LocalDateTime.parse(column.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));break;
            }
        });
        return user;
    }
}
