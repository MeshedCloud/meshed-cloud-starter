package cn.meshed.cloud.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Set;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
@RefreshScope
@ConfigurationProperties("service.security")
public class SecurityConfig {

    private String secret;
    private Set<String> excludeUris;
}
