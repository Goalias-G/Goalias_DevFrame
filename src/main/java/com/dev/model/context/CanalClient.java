package com.dev.model.context;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.dev.model.canal.UserCanalHandleServiceImpl;
import com.dev.model.config.CanalConfig;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CanalClient implements ApplicationRunner {

    @Resource
    private UserCanalHandleServiceImpl canalHandleService;

    @Resource
    private CanalConfig canalConfig;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final int BATCH_SIZE = 1000;

    @Override
    @Async("canal")
    public void run(ApplicationArguments args) throws Exception {
        CanalConnector canalConnector = canalConfig.createCanalConnector();
        try {
            canalConnector.connect();
            canalConnector.rollback();
            canalConnector.subscribe(canalConfig.getSchema()+"\\."+canalConfig.getUserTable());
        } catch (Exception e) {
            logger.warn(ExceptionUtil.getMessage(e));
            logger.warn("########canal连接失败#######");
            return;
        }
        while (true){
            Message message = canalConnector.getWithoutAck(BATCH_SIZE);
            long batchId = message.getId();
            if (batchId == -1 || message.getEntries().isEmpty()){
                try {
                    logger.info("canal监听{}:{}数据ing", canalConfig.getSchema(),canalConfig.getUserTable());
                    TimeUnit.SECONDS.sleep(2);
                }catch (InterruptedException e){
                    logger.warn("canal监听异常:{}", ExceptionUtil.stacktraceToString(e));
                }
            }else {
                handleMessage(message);
                canalConnector.ack(batchId);
            }

        }
    }


    private void handleMessage(Message message) {
        message.getEntries().forEach(entry -> {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                return;
            }
            CanalEntry.RowChange rowChange= null;
            try {
                 rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                ExceptionUtil.stacktraceToString(e);
            }
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            switch (rowChange.getEventType()){
                case CREATE://DDL
                    if (!canalConfig.isCreate()) break;
                    logger.info("[create]SqlLocation: {}-{}",entry.getHeader().getSchemaName(),entry.getHeader().getTableName());
                    canalHandleService.createSql(rowDatasList);
                    break;
                case QUERY :
                    if (!canalConfig.isSelect()) break;
                    logger.info("[select]SqlLocation: {}-{}",entry.getHeader().getSchemaName(),entry.getHeader().getTableName());
                    canalHandleService.selectSql(rowDatasList);
                    break;
                case INSERT:
                    if (!canalConfig.isInsert()) break;
                    logger.info("[insert]binlog:[{}:{}],SqlLocation: {}-{}",entry.getHeader().getLogfileName(),entry.getHeader().getLogfileOffset(),entry.getHeader().getSchemaName(),entry.getHeader().getTableName());
                    logger.info("[insert]sql => {}",rowChange.getSql());
                    canalHandleService.insertSql(rowDatasList);
                    break;
                case UPDATE:
                    if (!canalConfig.isUpdate()) break;
                    logger.info("[update]binlog:[{}:{}],SqlLocation: {}-{}",entry.getHeader().getLogfileName(),entry.getHeader().getLogfileOffset(),entry.getHeader().getSchemaName(),entry.getHeader().getTableName());
                    logger.info("[update]sql => {}",rowChange.getSql());
                    canalHandleService.updateSql(rowDatasList);
                    break;
                case DELETE:
                    if (!canalConfig.isDelete()) break;
                    logger.info("[delete]binlog:[{}:{}],SqlLocation: {}-{}",entry.getHeader().getLogfileName(),entry.getHeader().getLogfileOffset(),entry.getHeader().getSchemaName(),entry.getHeader().getTableName());
                    logger.info("[delete]sql => {}",rowChange.getSql());
                    canalHandleService.deleteSql(rowDatasList);
                    break;
                default:
                    logger.info("[other]EventType:{},SqlLocation: {}-{}",rowChange.getEventType(),entry.getHeader().getSchemaName(),entry.getHeader().getTableName());
            }
        });

    }


}
