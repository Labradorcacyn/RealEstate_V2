package salesianos.triana.dam.RealEstate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import salesianos.triana.dam.RealEstate.users.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
/* Ya no ignoramos la seguridad
    @Override
    //Ignora la seguridad para las peticiones
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().anyRequest();
    }*/

    //private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(customBasicAuthenticationEntryPoint)
                .and() //Gestionamos la seguridad de las peticiones //Configuramos el control de acceso
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/producto/**", "/lote/**").hasRole("PROPIETARIO")
                    .antMatchers(HttpMethod.POST, "/producto/**", "/lote/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/producto/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/producto/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/pedido/**").hasAnyRole("PROPIETARIO","ADMIN")
                    .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
