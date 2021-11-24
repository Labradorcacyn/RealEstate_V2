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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import salesianos.triana.dam.RealEstate.security.jwt.JwtAccessDeniedJHandler;
import salesianos.triana.dam.RealEstate.security.jwt.JwtAuthorizationFilter;
import salesianos.triana.dam.RealEstate.users.repository.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthorizationFilter filter;
    private final JwtAccessDeniedJHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/propietario/").permitAll()
                .antMatchers(HttpMethod.GET, "/propietario/{id}").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers(HttpMethod.GET, "/vivienda/").permitAll()
                .antMatchers(HttpMethod.GET, "/vivienda/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/inmobiliaria/{id}/gestor/").hasAnyRole("GESTOR","ADMIN")
                .antMatchers(HttpMethod.GET, "/inmobiliaria/").permitAll()
                .antMatchers(HttpMethod.GET, "/inmobiliaria/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/interesado/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/interesado/{id}").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers(HttpMethod.GET, "/vivienda/top?n=10").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register/user").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/register/gestor").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/vivienda/").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/vivienda/{id}/inmobiliaria/{id2}").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers(HttpMethod.POST, "/inmobiliaria/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "//inmobiliaria/{id}/gestor/").hasAnyRole("GESTOR","ADMIN")
                .antMatchers(HttpMethod.POST, "/vivienda/{id}/meinteresa").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.DELETE, "/propietario/{id}").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/vivienda/{id}").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/vivienda/{id}/inmobiliaria/").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/inmobiliaria/gestor/{id}").hasAnyRole("GESTOR","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/inmobiliaria/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/vivienda/{id}/meinteresa/").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers(HttpMethod.PUT, "/vivienda/{id}").hasAnyRole("PROPIETARIO","ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();

    }
}
