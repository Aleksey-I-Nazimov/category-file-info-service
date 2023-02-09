package org.numamo.category.file.info.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo.USER_AUTHORITY;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String USER_API_PREFIX = "/api";
    private static final String USER_API_ANT_MATCHER = USER_API_PREFIX + "/**";

    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity httpSecurity
    ) throws Exception {
        return httpSecurity
                .formLogin(withDefaults())
                .authorizeRequests()
                .antMatchers(USER_API_ANT_MATCHER)
                .hasAuthority(USER_AUTHORITY)
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
