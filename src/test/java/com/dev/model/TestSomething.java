package com.dev.model;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.dev.model.pojo.dto.UserDto;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest
public class TestSomething {
    @Resource
    private MinioClient minioClient;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Test
    public void test(){
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setPassword("<PASSWORD>");
        applicationEventPublisher.publishEvent(userDto);
    }
    @Test
    public void testCanal() {
        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(11111), "example", "", "");
        canalConnector.connect();
        canalConnector.subscribe("goalias\\..*");
        /**
         * 订阅规则
         * 1.  所有表：.*   or  .*\\..*
         * 2.  canal schema下所有表： canal\\..*
         * 3.  canal下的以canal打头的表：canal\\.canal.*
         * 4.  canal schema下的一张表：canal\\.test1
         * 5.  多个规则组合使用：canal\\..*,mysql.test1,mysql.test2 (逗号分隔)
         */
        canalConnector.rollback();
        int batchSize = 100;
        while (true) {
            // 获取一批数据，不一定会获取到 batchSize 条
            Message message = canalConnector.getWithoutAck(batchSize);
            // 获取批次id
            long batchId = message.getId();
            // 获取数据
            List<CanalEntry.Entry> entries = message.getEntries();
            if (batchId == -1 || entries.isEmpty()) {
                System.out.println("没有获取到数据");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            for (CanalEntry.Entry entry : entries) {
                if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                    continue;
                }

                CanalEntry.RowChange rowChange;
                try {
                    rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                } catch (Exception e) {
                    throw new RuntimeException("解析binlog数据出现异常 , data:" + entry, e);
                }

                CanalEntry.EventType eventType = rowChange.getEventType();
                System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                        entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                        entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                        eventType));

                if (eventType == CanalEntry.EventType.QUERY || rowChange.getIsDdl()) {
                    System.out.println("sql => " + rowChange.getSql());
                }

                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    if (eventType == CanalEntry.EventType.DELETE) {
                        printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        printColumn(rowData.getAfterColumnsList());
                    } else {
                        System.out.println("-------> before");
                        printColumn(rowData.getBeforeColumnsList());
                        System.out.println("-------> after");
                        printColumn(rowData.getAfterColumnsList());
                    }
                }
            }
            canalConnector.ack(batchId);
        }
    }
    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    @Test
    public void test2() {

    }
}
