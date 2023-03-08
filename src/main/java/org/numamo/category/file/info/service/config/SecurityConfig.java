package org.numamo.category.file.info.service.config;


import org.numamo.category.file.info.service.component.api.main.http.AuthenticationHandler;
import org.numamo.category.file.info.service.component.api.main.http.AuthenticationLogoutHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

import static java.util.Collections.singletonList;
import static org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo.USER_AUTHORITY;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;


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

    private static final Logger LOGGER = getLogger(SecurityConfig.class);

    private static final String ROOT_MATCHER = "/**";
    public static final String USER_API_PREFIX = "/api";
    private static final String USER_API_ANT_MATCHER = USER_API_PREFIX + ROOT_MATCHER;
    private static final String LOGIN_AUTH_KEY = "login";
    private static final String PASSWORD_AUTH_KEY = "key";
    private static final String AUTH_COOKIE_NAME = "JSESSIONID";

    private int maxSessionNumber;
    private String securityCors;

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

    @PostConstruct
    public void init() {
        LOGGER.debug("The security config was initialized: {}", this);
    }

    @Value("${server.servlet.session.max-session-number:5}")
    public void setMaxSessionNumber(Integer maxSessionNumber) {
        this.maxSessionNumber = maxSessionNumber;
    }

    @Value("${server.servlet.session.security.cors-origin:http://localhost:3000}")
    public void setSecurityCors(String securityCors) {
        this.securityCors = securityCors;
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
                .maximumSessions(maxSessionNumber)
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

        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(singletonList(securityCors));
        corsConfiguration.setAllowedMethods(singletonList("*"));
        corsConfiguration.setAllowedHeaders(singletonList("*"));

        final UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration(ROOT_MATCHER, corsConfiguration);

        final FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource));
        bean.setOrder(HIGHEST_PRECEDENCE);
        return bean;
    }

    @Override
    public String toString() {
        return "SecurityConfig{" +
                "maxSessionNumber=" + maxSessionNumber +
                ", securityCors='" + securityCors + '\'' +
                ", authenticationHandler=" + authenticationHandler +
                ", authenticationLogoutHandler=" + authenticationLogoutHandler +
                '}';
    }

}
