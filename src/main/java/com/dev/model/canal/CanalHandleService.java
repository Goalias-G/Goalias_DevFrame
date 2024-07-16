package com.dev.model.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

public interface CanalHandleService {

    void createSql(List<CanalEntry.RowData> rowDataList);

    void selectSql(List<CanalEntry.RowData> rowDataList);

    void insertSql(List<CanalEntry.RowData> rowDataList);

    void updateSql(List<CanalEntry.RowData> rowDataList);

    void deleteSql(List<CanalEntry.RowData> rowDataList);
}
