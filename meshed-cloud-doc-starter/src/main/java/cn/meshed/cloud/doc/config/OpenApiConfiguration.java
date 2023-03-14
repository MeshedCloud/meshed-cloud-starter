package cn.meshed.cloud.doc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class OpenApiConfiguration {

    private final OpenApiProperties openAPIProperties;

    public OpenApiConfiguration(OpenApiProperties openAPIProperties) {
        this.openAPIProperties = openAPIProperties;
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .contact(new Contact()
                                .email(openAPIProperties.getContactEmail())
                                .name(openAPIProperties.getContactName())
                                .url(openAPIProperties.getContactUrl())
                        )
                        .title(openAPIProperties.getTitle())
                        .version(openAPIProperties.getVersion())
                        .description(openAPIProperties.getDescription())
                        .termsOfService(openAPIProperties.getServiceUrl())
                        .license(new License().name(openAPIProperties.getLicense())
                                .url(openAPIProperties.getServiceUrl())));
    }


}