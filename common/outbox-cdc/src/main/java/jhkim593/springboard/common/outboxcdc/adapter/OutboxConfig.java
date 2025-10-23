package jhkim593.springboard.common.outboxcdc.adapter;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("jhkim593.springboard.common.outboxcdc")
@EntityScan("jhkim593.springboard.common.outboxcdc")
@EnableJpaRepositories("jhkim593.springboard.common.outboxcdc")
public class OutboxConfig {
}
