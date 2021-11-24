package salesianos.triana.dam.RealEstate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean //Para injectarlo en el UsuarioService
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}
