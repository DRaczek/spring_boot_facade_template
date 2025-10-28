package draczek.facadetemplate.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Auditing config class.
 */
@Configuration
@EnableJpaAuditing
class AuditConfig {
}