package cn.meshed.cloud.context.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Set;

/**
 * <h1>服务配置</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RefreshScope
@Data
@ConfigurationProperties("service")
public class ServiceConfig {

    private MockConfig mock;

    @Data
    public static class MockConfig {
        private Boolean enable;
        private String userId;
        private String username;
        private String access;
        private String roles;
    }
}
