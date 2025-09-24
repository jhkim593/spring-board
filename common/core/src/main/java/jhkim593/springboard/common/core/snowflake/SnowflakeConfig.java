package jhkim593.springboard.common.core.snowflake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {
    @Bean
    public DBIdGenerator dbIdGenerator() {
        return new Snowflake();
    }
}