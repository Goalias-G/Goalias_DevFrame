package com.aigc.vrmetal.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gws
 * @since 2024-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fish_veg_number")
@ApiModel(value="FishVegNumber对象", description="")
public class FishVegNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private Integer 青鱼;

    private Integer 草鱼;

    private Integer 鲢鱼;

    private Integer 鲫鱼;

    private Integer 鲤鱼;

    private Integer 鲶鱼;

    private Integer 菠菜;

    private Integer 白菜;

    private Integer 辣椒;

    private Integer 香菇;

    private Integer 南瓜;

    private Integer 红薯;

    private Integer 茄子;

    private Integer 番茄;

    private Integer 玉米;


}
