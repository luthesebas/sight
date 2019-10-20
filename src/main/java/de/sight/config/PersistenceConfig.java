package de.sight.config;

import de.sight.persistence.Auditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.sight.persistence")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    @Bean
    public AuditorAware<String> auditorProvider () {
        return new Auditor();
    }

}
