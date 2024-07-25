package com.dev.model.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public interface RedisService {

    /**
     * @description: 保存属性
     * @param key
     * @param value
     * @param time
    */
    void set(String key, Object value, long time);

    /**
     * @description: 保存属性
     * @param key
     * @param value
    */
    void set(String key, Object value);

    /**
     * @description: 获取属性
     * @param key
     * @return {@link Object}
    */
    Object get(String key);

    /**
     * @description: 删除属性
     * @param key
     * @return {@link Boolean}
    */
    Boolean del(String key);

    /**
     * @description: 批量删除属性
     * @param keys
     * @return {@link Long}
    */
    Long del(List<String> keys);

    /**
     * @description: 设置过期时间
     * @param key
     * @param time
     * @return {@link Boolean}
    */
    Boolean expire(String key, long time);

    /**
     * @description: 获取过期时间
     * @param key
     * @return {@link Long}
    */
    Long getExpire(String key);

    /**
     * @description: 判断key是否存在
     * @param key
     * @return {@link Boolean}
    */
    Boolean hasKey(String key);

    /**
     * @description: 按delta递增
     * @param key
     * @param delta
     * @return {@link Long}
    */
    Long incr(String key, long delta);

    /**
     * @description: 设定过期时间的递增1
     * @param key
     */
    Long incrExpire(String key, long time);

    /**
     * @description: 按delta递减
     * @param key
     * @param delta
     * @return {@link Long}
    */
    Long decr(String key, long delta);

    /**
     * @description: 获取Hash结构中的属性
     * @param key
     * @param hashKey
     * @return {@link Object}
    */
    Object hGet(String key, String hashKey);

    /**
     * @description: 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
     * @param time
     * @return {@link Boolean}
    */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * @description: 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
    */
    void hSet(String key, String hashKey, Object value);

    /**
     * @description: 获取hash结构中所有属性
     * @param key
     * @return {@link Map<String,Object>}
    */
    Map<String, Object> hmGet(String key);

    /**
     * @description: 添加多个hash结构
     * @param key
     * @param value
     * @param time
     * @return {@link Boolean}
    */
    Boolean hmSet(String key, Map<String, Object> value, long time);

    /**
     * @description: 添加多个hash结构
     * @param key
     * @param value
    */
    void hmSet(String key, Map<String, ?> value);

    /**
     * @description: 删除Hash结构中的属性
     * @param key
     * @param hashKeys
    */
    void hDel(String key, Object... hashKeys);

    /**
     * @description: 判断Hash结构中是否有该属性
     * @param key
     * @param hashKey
     * @return {@link Boolean}
    */
    Boolean hHasKey(String key, String hashKey);

    /**
     * @description: Hash结构中属性递增
     * @param key
     * @param hashKey
     * @param delta
     * @return {@link Long}
    */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * @description: Hash结构中属性递减
     * @param key
     * @param hashKey
     * @param delta
     * @return {@link Long}
    */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * @description: 获取Hash结构长度
     * @param key
     * @return {@link Long}
    */
    Long hSize(String key);

    /**
     * @description: 有序集合中数据递增
     * @param key
     * @param value
     * @param score
     * @return {@link Double}
    */
    Double zIncr(String key, Object value, Double score);

    /**
     * @description: 有序集合中数据递减
     * @param key
     * @param value
     * @param score
     * @return {@link Double}
    */
    Double zDecr(String key, Object value, Double score);

    /**
     * @description: 根据分数排名获取指定元素信息
     * @param key
     * @param start
     * @param end
     * @return {@link Map<Object,Double>}
    */
    Map<Object, Double> zReverseRangeWithScore(String key, long start, long end);

    /**
     * @description: 获取指定元素分数
     * @param key
     * @param value
     * @return {@link Double}
    */
    Double zScore(String key, Object value);

    /**
     * @description: 获取所有分数
     * @param key
     * @return {@link Map<Object,Double>}
    */
    Map<Object, Double> zAllScore(String key);

    /**
     * @description: 删除指定Zset元素
     * @param key
     * @param value
     * @return {@link Long}
    */
    Long zRem(String key, Object... value);

    /**
     * @description: 获取Set结构
     * @param key
     * @return {@link Set<Object>}
    */
    Set<Object> sMembers(String key);

    /**
     * @description: 随机获取指定数量的Set
     * @param key
     * @param count
     * @return {@link List<Object>}
    */
    List<Object> sRandMembers(String key, Long count);

    /**
     * @description: 随机获取Set
     * @param key
     * @return {@link Object}
    */
    Object sRandMember(String key);

    /**
     * @description: 获取不同的随机成员
     * @param key
     * @param count
     * @return {@link Set<Object>}
    */
    Set<Object> sDistinctRandomMembers(String key, Long count);

    /**
     * @description: 向Set结构中添加属性
     * @param key
     * @param values
     * @return {@link Long}
    */
    Long sAdd(String key, Object... values);

    /**
     * @description: 向Set结构中添加属性
     * @param key
     * @param time
     * @param values
    */
    Long sAddExpire(String key, Long time, Object... values);

    /**
     * @description: 是否为Set中的属性
     * @param key
     * @param value
     * @return {@link Boolean}
    */
    Boolean sIsMember(String key, Object value);

    /**
     * @description: Set的长度
     * @param key
     * @return {@link Long}
    */
    Long sSize(String key);

    /**
     * @description: 删除Set中的属性
     * @param key
     * @param values
     * @return {@link Long}
    */
    Long sRemove(String key, Object... values);

    /**
     * @description: 删除Set中的属性
     * @param key
     * @param start
     * @param end
     * @return {@link List<Object>}
    */
    List<Object> lRange(String key, long start, long end);

    /**
     * @description: 获取List中的长度
     * @param key
     * @return {@link Long}
    */
    Long lSize(String key);

    /**
     * @description: 根据索引获取List中的属性
     * @param key
     * @param index
     * @return {@link Object}
    */
    Object lIndex(String key, long index);

    /**
     * @description: 向List中添加属性
     * @param key
     * @param value
    */
    Long lPush(String key, Object value);

    /**
     * @description: 向List中添加属性
     * @param key
     * @param value
     * @param time
     * @return {@link Long}
    */
    Long lPush(String key, Object value, long time);

    /**
     * @description: 向List中批量添加属性
     * @param key
     * @param values
     * @return {@link Long}
    */
    Long lPushAll(String key, Object... values);

    /**
     * @description: 向List中批量添加属性
     * @param key
     * @param time
     * @param values
     * @return {@link Long}
     * @auther apecode
     * @date 2022/5/29 19:03
    */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * @description: 从List中移除属性
     * @param key
     * @param count
     * @param value
     * @return {@link Long}
    */
    Long lRemove(String key, long count, Object value);

    /**
     * @description: 向Bitmap中新增值
     * @param key
     * @param offset
     * @param b
     * @return {@link Boolean}
    */
    Boolean bitAdd(String key, int offset, boolean b);
    
    /**
     * @description: 从Bitmap中获取偏移量的值
     * @param key
     * @param offset
     * @return {@link Boolean}
    */
    Boolean bitGet(String key, int offset);

    /**
     * @description: 获取Bitmap的key值总和
     * @param key
     * @return {@link Long}
    */
    Long bitCount(String key);

    /**
     * @description: 获取Bitmap范围值
     * @param key
     * @param limit
     * @param offset
     * @return {@link List<Long>}
    */
    List<Long> bitField(String key, int limit, int offset);

    /**
     * @description: 获取所有Bitmap
     * @param key
     * @return {@link byte}
     * @auther apecode
     * @date 2022/5/29 19:04
    */
    byte[] bitGetAll(String key);

    /**
     * @description: 向hyperlog中添加数据
     * @param key
     * @param value
     * @return {@link Long}
    */
    Long hyperAdd(String key, Object... value);

    /**
     * @description: 获取hyperlog元素数量
     * @param key
     * @return {@link Long}
    */
    Long hyperGet(String... key);

    /**
     * @description: 删除hyperlog数据
     * @param key
    */
    void hyperDel(String key);

    /**
     * @description: 增加坐标
     * @param key
     * @param x
     * @param y
     * @param name
     * @return {@link Long}
    */
    Long geoAdd(String key, Double x, Double y, String name);

    /**
     * @description: 根据城市名称获取坐标集合
     * @param key
     * @param place
     * @return {@link List<Point>}
    */
    List<Point> geoGetPointList(String key, Object... place);

    /**
     * @description: 计算两个城市之间的距离
     * @param key
     * @param placeOne
     * @param placeTow
     * @return {@link Distance}
    */
    Distance geoCalculationDistance(String key, String placeOne, String placeTow);

    /**
     * @description: 获取附该地点附近的其他地点
     * @param key
     * @param place
     * @param distance
     * @param limit
     * @param sort
     * @return {@link GeoResults<GeoLocation<Object>>}
    */
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoNearByPlace(String key, String place, Distance distance, long limit, Sort.Direction sort);

    /**
     * @description: 获取地点的hash
     * @param key
     * @param place
     * @return {@link List<String>}
    */
    List<String> geoGetHash(String key, String... place);

}
