package org.numamo.category.file.info.service.component.main.user;


import org.numamo.category.file.info.service.component.api.main.user.UserInfoComponent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * The main SpringSecurity service for searching the user data with in database
 *
 * @author Nazimov Aleksey I.
 */
@Component
public final class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = getLogger(UserDetailsServiceImpl.class);

    private final UserInfoComponent userInfoComponent;

    @Autowired
    public UserDetailsServiceImpl(UserInfoComponent userInfoComponent) {
        this.userInfoComponent = userInfoComponent;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userInfoComponent.loadUserByLogin(username);

        } catch (Exception e) {
            LOGGER.trace("The user name/login was not found: {}", username);
            throw new UsernameNotFoundException("The user name/login was not found: " + username, e);
        }
    }

}
