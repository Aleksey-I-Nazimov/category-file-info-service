package org.numamo.category.file.info.service.component.api.main.provider;


/**
 * The interface of user session provider.
 * Provider creates or updates the current user session.
 * All user sessions can be expired. Expiration timeout depends on the server timeout configuration.
 * (see application.yaml) The provider has no idea about requested session because the session
 * can exist but has already been expired and that is why it has to be recreated or updated.
 * To recreate session we have to use so-called user ID masked as a user login.
 * To update session we have to use the session ID only.
 * <p>
 * Let's consider three corner cases:
 * <p>
 * 1 - If the requested session was not expired it will be updated and session time counter will be restarted
 * 2 - If the session was not created it will be created by the login.
 * 3 - If the session was expired it will be created again.
 * <p>
 * Provider automatically controls all user sessions and clean them periodically according
 * to the user request. Each user request executes session checking.
 *
 * @author Nazimov Aleksey I.
 */
public interface UserSessionProvider {

    /**
     * The main method of session management.
     * <p>
     * This method automatically makes the following actions:
     * 1- Creating the new session by user login
     * 2- Updating existed session by session ID
     * 3- Checking all session timeouts
     *
     * @param userLogin        is the user ID for making the new sessions
     * @param existedSessionId is the session ID to find and check existed sessions
     * @return session ID. The ID can be represented by the new session ID or the previous one
     */
    long recreateSession(String userLogin, Long existedSessionId);

}
