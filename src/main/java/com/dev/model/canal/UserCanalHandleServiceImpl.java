package com.dev.model.canal;

import cn.hutool.json.JSONUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.dev.model.context.constant.RedisConstants;
import com.dev.model.pojo.entity.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserCanalHandleServiceImpl implements CanalHandleService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void createSql(List<CanalEntry.RowData> rowDataList) {

    }

    @Override
    public void insertSql(List<CanalEntry.RowData> rowDataList) {
        rowDataList.forEach(rowData -> {
            User user = getCanalEntity(new User(), rowData.getAfterColumnsList());
            stringRedisTemplate.opsForValue().set(RedisConstants.USER +user.getId(), JSONUtil.toJsonStr(user));
        });
    }

    @Override
    public void updateSql(List<CanalEntry.RowData> rowDataList) {
        rowDataList.forEach(rowData -> {
            User beforeUser = getCanalEntity(new User(), rowData.getBeforeColumnsList());
            User afterUser = getCanalEntity(new User(), rowData.getAfterColumnsList());
            stringRedisTemplate.opsForValue().set(RedisConstants.USER+afterUser.getId(), JSONUtil.toJsonStr(afterUser));
        });
    }

    @Override
    public void deleteSql(List<CanalEntry.RowData> rowDataList) {

    }
}
