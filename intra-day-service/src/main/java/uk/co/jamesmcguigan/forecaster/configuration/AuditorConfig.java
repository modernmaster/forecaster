package uk.co.jamesmcguigan.forecaster.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uk.co.jamesmcguigan.forecaster.repository.auditing.AuditorAware;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {
    @Bean
    public AuditorAware auditorProvider(){
        return new AuditorAware();
    }
}
