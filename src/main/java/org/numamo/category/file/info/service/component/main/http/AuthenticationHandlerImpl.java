package org.numamo.category.file.info.service.component.main.http;

import org.numamo.category.file.info.service.component.api.main.http.AuthenticationHandler;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public final class AuthenticationHandlerImpl implements AuthenticationHandler {

    private static final Logger LOGGER = getLogger(AuthenticationHandlerImpl.class);

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        // TODO: Let's extract the remote network information and log it

        LOGGER.warn("The input LOGIN request was not permitted: {}", request);
        // Let's forbid the bad credentials without change location
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        LOGGER.info("The input LOGIN request was authenticated SUCCESSFULLY: {}", request);
        // Let's not set the new location. Just set OK status
        response.setStatus(HttpStatus.OK.value());
    }
}
