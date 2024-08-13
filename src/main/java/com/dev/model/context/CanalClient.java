package com.dev.model.context;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.dev.model.canal.CanalHandleEnum;
import com.dev.model.canal.CanalHandleService;
import com.dev.model.canal.UserCanalHandleServiceImpl;
import com.dev.model.config.CanalConfig;
import com.dev.model.context.exception.BizException;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CanalClient implements ApplicationRunner, ApplicationContextAware {

    @Resource
    private CanalConfig canalConfig;

    @Getter
    private static ApplicationContext context;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final int BATCH_SIZE = 1000;

    @Override
    @Async
    public void run(ApplicationArguments args) throws Exception {
        CanalConnector canalConnector = canalConfig.createCanalConnector();
        try {
            canalConnector.connect();
            canalConnector.rollback();
            canalConnector.subscribe(canalConfig.getSchema() + "\\." + canalConfig.getTable());
        } catch (Exception e) {
            logger.warn(ExceptionUtil.getMessage(e));
            logger.warn("########canal连接失败#######");
            return;
        }
        while (true) {
            try {
                Message message = canalConnector.getWithoutAck(BATCH_SIZE);
                long batchId = message.getId();
                if (batchId == -1 || message.getEntries().isEmpty()) {

                    logger.info("canal监听{}->{}数据···", canalConfig.getSchema(), canalConfig.getTable());
                    TimeUnit.SECONDS.sleep(2);
                } else {
                    handleMessage(message);
                    canalConnector.ack(batchId);
                }
            } catch (CanalClientException e) {
                throw new BizException("canal监听异常:" + e.getMessage());
            }

        }
    }


    private void handleMessage(Message message) {
        message.getEntries().forEach(entry -> {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                return;
            }
            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                ExceptionUtil.stacktraceToString(e);
            }
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            CanalHandleService handler = (CanalHandleService)getContext()//要求枚举类和表名匹配
                    .getBean(CanalHandleEnum.valueOf(entry.getHeader().getTableName().toUpperCase()).getHandler());
            switch (rowChange.getEventType()) {
                case CREATE://DDL
                    if (!canalConfig.isCreate()) break;
                    logger.info("[create]SqlLocation: {}-{}", entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                    handler.createSql(rowDatasList);
                    break;
                case QUERY:
                    if (!canalConfig.isSelect()) break;
                    logger.info("[select]SqlLocation: {}-{}", entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                    handler.selectSql(rowDatasList);
                    break;
                case INSERT:
                    if (!canalConfig.isInsert()) break;
                    logger.info("[insert]binlog:[{}:{}],SqlLocation: {}-{}", entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(), entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                    handler.insertSql(rowDatasList);
                    break;
                case UPDATE:
                    if (!canalConfig.isUpdate()) break;
                    logger.info("[update]binlog:[{}:{}],SqlLocation: {}-{}", entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(), entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                    handler.updateSql(rowDatasList);
                    break;
                case DELETE:
                    if (!canalConfig.isDelete()) break;
                    logger.info("[delete]binlog:[{}:{}],SqlLocation: {}-{}", entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(), entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                    handler.deleteSql(rowDatasList);
                    break;
                default:
                    logger.info("[other]EventType:{},SqlLocation: {}-{}", rowChange.getEventType(), entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
            }
        });

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
