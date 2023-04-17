package cn.meshed.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <h1>Jackson 配置</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class JacksonMapperAutoConfiguration {

    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(new JacksonMapper());
    }
}
