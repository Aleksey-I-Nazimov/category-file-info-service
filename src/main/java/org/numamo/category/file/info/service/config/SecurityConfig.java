package org.numamo.category.file.info.service.config;


import org.numamo.category.file.info.service.component.api.main.http.AuthenticationHandler;
import org.numamo.category.file.info.service.component.api.main.http.AuthenticationLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

import static org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo.USER_AUTHORITY;


/**
 * The main spring security config.
 * Additional info can be read here:
 * -> https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
 * -> https://www.baeldung.com/spring-security-login
 *
 *  Additional references:
 *  https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/csrf.html
 *  https://spring.io/guides/gs/securing-web/
 *  https://github.com/spring-projects/spring-authorization-server
 *  https://www.baeldung.com/spring-security-oauth-auth-server
 *  https://stackoverflow.com/questions/70092103/spring-data-jpa-lock-annotation-with-transactional
 *  https://www.postgresql.org/docs/current/explicit-locking.html
 *
 *
 * @author Nazimov Aleksey I.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String USER_API_PREFIX = "/api";
    private static final String USER_API_ANT_MATCHER = USER_API_PREFIX + "/**";
    private static final String LOGIN_AUTH_KEY = "login";
    private static final String PASSWORD_AUTH_KEY = "key";
    private static final String AUTH_COOKIE_NAME = "JSESSIONID";

    private final AuthenticationHandler authenticationHandler;
    private final AuthenticationLogoutHandler authenticationLogoutHandler;

    @Autowired
    public SecurityConfig(
            AuthenticationHandler authenticationHandler,
            AuthenticationLogoutHandler authenticationLogoutHandler
    ) {
        this.authenticationHandler = authenticationHandler;
        this.authenticationLogoutHandler = authenticationLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity httpSecurity
    ) throws Exception {
        return httpSecurity
                .formLogin()
                .successHandler(authenticationHandler)
                .failureHandler(authenticationHandler)
                .usernameParameter(LOGIN_AUTH_KEY)
                .passwordParameter(PASSWORD_AUTH_KEY)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies(AUTH_COOKIE_NAME)
                .logoutSuccessHandler(authenticationLogoutHandler)
                .and()
                .authorizeRequests()
                .antMatchers(USER_API_ANT_MATCHER)
                .hasAuthority(USER_AUTHORITY)
                .antMatchers(HttpMethod.OPTIONS, USER_API_ANT_MATCHER)
                .hasAuthority(USER_AUTHORITY)
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .maximumSessions(2)
                .maxSessionsPreventsLogin(true)
                .and()
                .sessionFixation()
                .newSession()
                .and()
                .csrf()
                .disable()
                .build();
    }

    /**
     * All user passwords were stored to the database and masked by this encoder
     *
     * @return the reference of the new password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // TODO: lets read the host from the configs
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
