package cn.jxh.learning.swagger.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Description 用户实体
 * @Author DXC
 **/
@Data
@Builder
@ApiModel("ユーザー対象")
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8133726412040750983L;

    private String username;
    private Integer age;

    /**
     * 利用地点ID
     */
    @JSONField(name = "service-site-id")
    @ApiModelProperty(name = "service-site-id", value = "利用地点ID")
    private String serviceSiteId;
}
