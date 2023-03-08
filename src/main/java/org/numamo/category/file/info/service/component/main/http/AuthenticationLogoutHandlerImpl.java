package org.numamo.category.file.info.service.component.main.http;

import org.numamo.category.file.info.service.component.api.main.http.AuthenticationLogoutHandler;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public final class AuthenticationLogoutHandlerImpl implements AuthenticationLogoutHandler {

    private static final Logger LOGGER = getLogger(AuthenticationLogoutHandlerImpl.class);

    @Override
    public void onLogoutSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) {
        // TODO: lets trace and audit some parameters like: https://www.baeldung.com/spring-security-logout
        LOGGER.info("The user called LOGOUT: {}, {}", request, authentication);
    }
}
