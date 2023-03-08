package org.numamo.category.file.info.service.component.api.main.session;


/**
 * The interface of user session manager.
 * The main goal is creating, updating and expiring user sessions which are bound to
 * the respective file index.
 * <p>
 * The file index is the numerical identifier which is closely connected with
 * scanning file system. File system can be changed independently that is why
 * we have to know ho many users use the current version described by the file index
 *
 * @author Nazimov Aleksey I.
 */
public interface UserSessionManager {

    /**
     * The method creates a new session, binds session to the current actual index.
     *
     * @param userLogin is the user login (so-called user ID)
     * @return the new created session ID
     */
    long createNewSession(String userLogin);

    /**
     * The method updates the session, connected with session ID
     *
     * @param sessionId is the session ID
     * @return TRUE means the session was found and updated, FALSE the session was not found
     */
    boolean updateUserSession(long sessionId);

    /**
     * The method retrieves the actual index and reads respective actual user sessions
     *
     * @return TRUE means existence user sessions FALSE means absence user sessions
     */
    boolean hasActiveUserSessions();

    /**
     * The method writes special FALSE actual flag to the expired sessions and
     * makes session management according to the user activity. Not supported sessions
     * are pointed as not actual sessions.
     */
    void cleanExpiredSessions();

}
