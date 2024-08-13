package com.dev.model.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.dev.model.context.exception.BizException;
import com.dev.model.utils.FieldsUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface CanalHandleService {

    void createSql(List<CanalEntry.RowData> rowDataList);

    void selectSql(List<CanalEntry.RowData> rowDataList);

    void insertSql(List<CanalEntry.RowData> rowDataList);

    void updateSql(List<CanalEntry.RowData> rowDataList);

    void deleteSql(List<CanalEntry.RowData> rowDataList);

    default <T> T getCanalEntity(T entity, List<CanalEntry.Column> columns){
        Map<String, String> fieldMap = columns.stream().collect(Collectors.toMap(column -> FieldsUtil.underLineToCamel(column.getName()), CanalEntry.Column::getValue));
        Class<?> clazz = entity.getClass();
        List<Field> columnList = Arrays.stream(clazz.getDeclaredFields()).toList();
        for (Field field : columnList) {
            String value = fieldMap.get(field.getName());//驼峰命名
            if (!StringUtils.hasText(value)) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object typeValue = convertValue(value, field.getType());
                field.set(entity, typeValue);
            } catch (IllegalAccessException e) {
                throw new BizException("canal类型转换异常");
            }
        }
        return (T) entity;
    }

    /**
     * 根据字段类型转换值
     */
    private Object convertValue(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value);
        } else if (targetType == short.class || targetType == Short.class) {
            return Short.parseShort(value);
        } else if (targetType == byte.class || targetType == Byte.class) {
            return Byte.parseByte(value);
        } else if (targetType == LocalDateTime.class){
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//需要可以加类型
        }else {
            throw new IllegalArgumentException("不支持的字段类型: " + targetType);
        }
    }
}
