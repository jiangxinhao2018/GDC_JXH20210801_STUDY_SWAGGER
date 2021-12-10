package cn.jxh.learning.swagger.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Description Swagger配置文件
 * @Author DXC
 **/
@Configuration
@EnableOpenApi
@ComponentScan(basePackages = "cn.jxh.learning.swagger.controller")
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
//                .enable(swaggerProperties.getEnable())
                //设定Api文档头信息，这个信息会展示在文档UI的头部位置
                .apiInfo(swaggerApiInfo())
//                .host(swaggerProperties.getTryHost())
//                .globalRequestParameters(globalBaseParameters())
                .select()
                //添加过滤条件，谓词过滤predicate，这里是自定义注解进行过滤
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createDeviceApi(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("デバイス管理")
//                .enable(swaggerProperties.getEnable())
                //设定Api文档头信息，这个信息会展示在文档UI的头部位置
                .apiInfo(swaggerApiInfo())
//                .host(swaggerProperties.getTryHost())
                .globalRequestParameters(globalAllParameters())
                .select()
                //添加过滤条件，谓词过滤predicate，这里是自定义注解进行过滤
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/devices.*"))
//                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createUserApi(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("ユーザー管理")
//                .enable(swaggerProperties.getEnable())
                .apiInfo(swaggerApiInfo())
//                .host(swaggerProperties.getTryHost())
                .globalRequestParameters(globalUserParameters())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/user.*"))
//                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createServiceApi(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("サービス管理")
//                .enable(swaggerProperties.getEnable())
                .apiInfo(swaggerApiInfo())
//                .host(swaggerProperties.getTryHost())
                .globalRequestParameters(globalAllParameters())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/services.*"))
//                .paths(PathSelectors.any())
                .build();
    }

    @Bean(value = "DUKE管理")
    public Docket createDukeApi(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("DUKE認証")
//                .enable(swaggerProperties.getEnable())
                .apiInfo(swaggerApiInfo())
//                .host(swaggerProperties.getTryHost())
//                .globalRequestParameters(globalRequestParameters())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.jxh.learning.swagger.controller"))
                .paths(PathSelectors.regex("/duke.*"))
//                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 自定义API文档基本信息实体
     * @return
     */
    private ApiInfo swaggerApiInfo(){
        Contact contact = new Contact("理解RESTful架构", null, "1026412257@qq.com");
        return new ApiInfoBuilder()
                    .title(swaggerProperties.getAppName() + " Api Doc")
                    .description(swaggerProperties.getAppDescription())
                    .contact(contact)
                    .version(swaggerProperties.getAppVersion())
                    .build();
    }

    private List<RequestParameter> globalUserParameters() {
        List<RequestParameter> keyList = new ArrayList<RequestParameter>();
        keyList.add(new RequestParameterBuilder().name("Authorization").required(true).description("認証情報").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("x-api-key").required(true).description("API-KEY").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("X-Cf-Secret").required(true).description("CloudFront 認証").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("x-tgiot-http-request-id").required(true).description("HTTPリクエストID").in(ParameterType.HEADER).build());
        return keyList;
    }

    private List<RequestParameter> globalAllParameters() {
        List<RequestParameter> keyList = new ArrayList<RequestParameter>();
        keyList.add(new RequestParameterBuilder().name("Authorization").required(true).description("認証情報").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("x-api-key").required(true).description("API-KEY").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("X-Cf-Secret").required(true).description("CloudFront 認証").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("x-tgiot-login-token").required(true).description("ログイントークン").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("x-tgiot-mobile-session-id").required(true).description("モバイルセッションID").in(ParameterType.HEADER).build());
        keyList.add(new RequestParameterBuilder().name("x-tgiot-http-request-id").required(true).description("HTTPリクエストID").in(ParameterType.HEADER).build());
        return keyList;
    }

}
