package salesianos.triana.dam.RealEstate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    public SpringSecurityAuditorAware auditorProvider(){
        return new SpringSecurityAuditorAware();
    }
}
