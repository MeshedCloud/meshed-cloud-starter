package cn.meshed.cloud.doc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@EnableSwagger2
public class Knife4jConfiguration {

    private final Knife4jProperties knife4jProperties;

    public Knife4jConfiguration(Knife4jProperties knife4jProperties) {
        this.knife4jProperties = knife4jProperties;
    }

    @Bean(value = "dockerBean")
    public Docket dockerBean() {
        //指定使用Swagger2规范
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //描述字段支持Markdown语法
                        .title(knife4jProperties.getTitle())
                        .description(knife4jProperties.getDescription())
                        .termsOfServiceUrl(knife4jProperties.getServiceUrl())
                        .contact(new Contact(knife4jProperties.getContactName(),knife4jProperties.getContactUrl(),knife4jProperties.getContactEmail()))
                        .version(knife4jProperties.getVersion())
                        .build())
                //分组名称
                .groupName(knife4jProperties.getGroupName())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }


}