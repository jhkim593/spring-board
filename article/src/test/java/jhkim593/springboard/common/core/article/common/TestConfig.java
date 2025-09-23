package jhkim593.springboard.common.core.article.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public DBCleanManager dbCleanManager() {
        return new DBCleanManager();
    }
}
