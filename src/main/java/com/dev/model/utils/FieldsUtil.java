package com.dev.model.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将from类中的非null值赋值到to,可以自己赋值自己，或者类赋值到相应的Record类，两个类里字段相同
 * ，有get/set方法就行。方便后续插入
 * @author zhaojianqiang
 */
public class FieldsUtil {
    private static Logger log = LoggerFactory.getLogger(FieldsUtil.class);
    /**
     * 下划线转驼峰正则表达式的Pattern
     */
    private static Pattern underLineToCamel = Pattern.compile("_(\\w)");

    /**
     * 对象相同属性赋值，不含null值属性
     * @param from
     * @param to
     * @return
     */
    public static Object assignNotNull(Object from, Object to) {
        return assignNotNull(from, to, null);
    }


    /**
     * 对象相同属性赋值，不含null值属性
     * @param from          来源对象
     * @param to            赋值对象
     * @param onlyPropNames 仅赋值的属性名字
     * @return
     */
    public static Object assignNotNull(Object from, Object to, List<String> onlyPropNames) {
        Class<?> clazz = from.getClass();
        Class<?> fromClazz = clazz;
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(List.of(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        Class<?> clazz2 = to.getClass();
        String uid = "serialVersionUID";
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!field.getName().equals(uid)) {
                    if (onlyPropNames != null && !onlyPropNames.contains(field.getName())) {
                        continue;
                    }
                    PropertyDescriptor pd = new PropertyDescriptor((String)field.getName(), fromClazz);
                    // 获得读方法
                    Method rm = pd.getReadMethod();
                    Object o = rm.invoke(from);
                    if(o == null){
                        continue;
                    }
                    PropertyDescriptor pd2 = new PropertyDescriptor((String)field.getName(), clazz2);
                    // 获得写方法
                    Method wm = pd2.getWriteMethod();
                    wm.invoke(to, o);
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
        return to;
    }

    /**
     * 对象相同属性赋值，不含null值和空字符串属性
     * @param from          来源对象
     * @param to            赋值对象
     * @param onlyPropNames 仅赋值的属性名字
     * @return
     */
    public static Object assignNotEmpty(Object from, Object to, List<String> onlyPropNames) {
        Class<?> clazz = from.getClass();
        Class<?> fromClazz = clazz;
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(List.of(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        Class<?> clazz2 = to.getClass();
        String uid = "serialVersionUID";
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!field.getName().equals(uid)) {
                    if (onlyPropNames != null && !onlyPropNames.contains(field.getName())) {
                        continue;
                    }
                    PropertyDescriptor pd = new PropertyDescriptor((String)field.getName(), fromClazz);
                    // 获得读方法
                    Method rm = pd.getReadMethod();
                    Object o = rm.invoke(from);
                    // 过滤null值和空字符串
                    if(o == null || (o instanceof String && StringUtils.isEmpty(o))){
                        continue;
                    }
                    PropertyDescriptor pd2 = new PropertyDescriptor((String)field.getName(), clazz2);
                    // 获得写方法
                    Method wm = pd2.getWriteMethod();
                    wm.invoke(to, o);
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
        return to;
    }

    /**
     * 对象字段值拷贝
     * @param fromObject    数据来源地方
     * @param toObject      数据目标对象
     * @param fieldAliasMap 类字段别名
     * @param ignoresField  类需要忽略字段
     * @param onlyField 指定的要设置的字段，指定外的字段不复制
     */
    public static void assign(Object fromObject, Object toObject, Map<String, String> fieldAliasMap, Set<String> ignoresField, Set<String> onlyField) {
        Class<?> fromClazz = fromObject.getClass();
        Class<?> toClazz = toObject.getClass();

        List<Field> fromClazzDeclaredFields = new ArrayList<>();

        Class<?> clazz = fromClazz;
        while (clazz != null) {
            fromClazzDeclaredFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        for (Field fromClazzDeclaredField : fromClazzDeclaredFields) {
            String fromFieldName = fromClazzDeclaredField.getName();
            // 需要忽略
            if (ignoresField != null && ignoresField.contains(fromFieldName)) {
                continue;
            }
            // 设置了指定字段
            if (onlyField != null && !onlyField.contains(fromFieldName)) {
                continue;
            }

            String toFieldName = fromFieldName;
            // 需要使用别名
            if (fieldAliasMap != null && fieldAliasMap.get(fromFieldName) != null) {
                toFieldName = fieldAliasMap.get(fromFieldName);
            }

            Method fromClazzReaderMethod, toClazzWriterMethod;
            try {
                PropertyDescriptor fromClazzPd = new PropertyDescriptor((String)fromFieldName, fromClazz);
                fromClazzReaderMethod = fromClazzPd.getReadMethod();
            } catch (IntrospectionException e) {
//                log.debug(fromClazz.getName() + ": not has getter/setter method for field " + fromFieldName);
                continue;
            }
            try {
                PropertyDescriptor toClazzPd = new PropertyDescriptor((String)toFieldName, toClazz);
                toClazzWriterMethod = toClazzPd.getWriteMethod();
            } catch (IntrospectionException e) {
//                log.debug(toClazz.getName() + ": not has  getter/setter method for field " + toFieldName);
                continue;
            }
            try {
                Object data = fromClazzReaderMethod.invoke(fromObject);
                if (data != null) {
                    toClazzWriterMethod.invoke(toObject, data);
                }
            } catch (IllegalAccessException | IllegalArgumentException |InvocationTargetException e) {
                log.debug("getter/setter method invode error:" + e.getMessage());
            }
        }
    }

    /**
     * 对象字段值拷贝
     * @param fromObject    数据来源地方
     * @param toObject      数据目标对象
     * @param fieldAliasMap 类字段别名
     */
    public static void assign(Object fromObject, Object toObject, Map<String, String> fieldAliasMap) {
        assign(fromObject, toObject, fieldAliasMap, null, null);
    }

    /**
     * 对象字段值拷贝
     * @param fromObject   数据来源地方
     * @param toObject     数据目标对象
     * @param ignoresField 类需要忽略字段
     */
    public static void assignWithIgnoreField(Object fromObject, Object toObject, Set<String> ignoresField) {
        assign(fromObject, toObject, null, ignoresField, null);
    }

    /**
     * 对象字段值拷贝
     * @param fromObject   数据来源地方
     * @param toObject     数据目标对象
     * @param onlyField 类需要忽略字段
     */
    public static void assignWithPointField(Object fromObject, Object toObject, Set<String> onlyField) {
        assign(fromObject, toObject, null, null, onlyField);
    }


    /**
     * 对象字段值拷贝
     * @param fromObject 数据来源地方
     * @param toObject   数据目标对象
     */
    public static void assign(Object fromObject, Object toObject) {
        assign(fromObject, toObject, null, null, null);
    }

    /**
     * 通过FieldName获取FieldValue（支持fieldName为下划线命名；支持基类属性）
     * @param <T>
     * @param fieldName 属性名
     * @param obj       对象
     * @param clz       返回值类型
     * @return 属性值（null）
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValueByFieldName(String fieldName, Object obj, Class<T> clz) {
        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }
        fieldName = underLineToCamel(fieldName);
        Class<?> objClass = obj.getClass();
        Field field = null;
        T t = null;
        for (; objClass != Object.class; objClass = objClass.getSuperclass()) {
            try {
                field = objClass.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    t = (T) (field.get(obj));
                    break;
                }
            } catch (Exception e) {
            }
        }
        return t;
    }

    /**
     * 下划线命名转驼峰
     * @param str
     * @return
     */
    public static String underLineToCamel(String str) {
        Matcher matcher = underLineToCamel.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 打印对象值
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        if (obj == null) {
            return "";
        }
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(obj.getClass().getSimpleName()).append("{");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append(field.getName()).append(":").append(field.get(obj)).append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.deleteCharAt(sb.length() - 1).append("}").toString();
    }

}
