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
 * @since 2024-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("drug")
@ApiModel(value="Drug对象", description="")
public class Drug implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "drug_id", type = IdType.AUTO)
    private Integer drugId;

    private String drugName;

    private String drugDetails;

    private Integer drugNum;


}
