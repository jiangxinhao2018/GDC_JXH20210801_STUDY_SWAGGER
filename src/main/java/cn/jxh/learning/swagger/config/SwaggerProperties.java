package cn.jxh.learning.swagger.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SwaggerProperties implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8419197422614722765L;

    @Value("${swagger.enable}")
    private Boolean enable;

    @Value("${swagger.appName}")
    private String appName;

    @Value("${swagger.appVersion}")
    private String appVersion;

    @Value("${swagger.appDescription}")
    private String appDescription;

    @Value("${swagger.tryHost}")
    private String tryHost;
}
