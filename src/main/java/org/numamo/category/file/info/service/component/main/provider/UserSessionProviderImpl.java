package org.numamo.category.file.info.service.component.main.provider;

import org.numamo.category.file.info.service.component.api.main.provider.UserSessionProvider;
import org.numamo.category.file.info.service.component.api.main.session.UserSessionManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;


@Component
public class UserSessionProviderImpl implements UserSessionProvider {

    private static final Logger LOGGER = getLogger(UserSessionProviderImpl.class);

    private final UserSessionManager userSessionManager;

    @Autowired
    public UserSessionProviderImpl(UserSessionManager userSessionManager) {
        this.userSessionManager = userSessionManager;
    }

    @Override
    @Transactional(isolation = READ_COMMITTED)
    public long recreateSession(
            final String userLogin,
            final Long existedSessionId
    ) {
        LOGGER.info("Recreating user session for user={},id={}", userLogin, existedSessionId);

        userSessionManager.cleanExpiredSessions();

        final long newUserSessionId;

        if (!userSessionManager.updateUserSession(existedSessionId)) {
            /*
            The user session was not found or not active. We create the new one
             */
            newUserSessionId = userSessionManager.createNewSession(userLogin);
        } else {
            /*
            The user session was updated and we set the user session ID as the new session ID
             */
            newUserSessionId = requireNonNull(existedSessionId, "Illegal NULL session ID");
        }

        LOGGER.info("Recreating user session was updated: user={},id={},newId={}", userLogin, existedSessionId, newUserSessionId);
        return newUserSessionId;
    }
}
