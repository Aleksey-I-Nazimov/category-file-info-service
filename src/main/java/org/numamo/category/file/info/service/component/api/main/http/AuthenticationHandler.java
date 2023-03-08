package org.numamo.category.file.info.service.component.api.main.http;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public interface AuthenticationHandler extends AuthenticationFailureHandler, AuthenticationSuccessHandler {
}
