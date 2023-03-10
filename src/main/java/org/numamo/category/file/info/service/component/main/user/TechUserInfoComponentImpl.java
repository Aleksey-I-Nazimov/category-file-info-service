package org.numamo.category.file.info.service.component.main.user;

import org.numamo.category.file.info.service.component.api.main.user.TechUserInfoComponent;
import org.numamo.category.file.info.service.component.api.main.user.UserComponent;
import org.numamo.category.file.info.service.component.api.main.user.UserInfoComponent;
import org.numamo.category.file.info.service.component.api.main.user.model.GrantedAuthorityDmo;
import org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo;
import org.numamo.category.file.info.service.config.api.AppConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo.USER_AUTHORITY;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;


@Component
public class TechUserInfoComponentImpl implements TechUserInfoComponent {

    private static final Logger LOGGER = getLogger(TechUserInfoComponentImpl.class);

    private final UserComponent userComponent;
    private final UserInfoComponent userInfoComponent;
    private final PasswordEncoder passwordEncoder;
    private final AppConfig appConfig;

    @Autowired
    public TechUserInfoComponentImpl(
            UserComponent userComponent,
            UserInfoComponent userInfoComponent,
            PasswordEncoder passwordEncoder,
            AppConfig appConfig
    ) {
        this.userComponent = userComponent;
        this.userInfoComponent = userInfoComponent;
        this.passwordEncoder = passwordEncoder;
        this.appConfig = appConfig;
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public void makeTechUser() {
        makeNewTechUser();
    }


    private void makeNewTechUser() {
        final Optional<UserDetailsDmo> userDetailsOpt = userInfoComponent
                .loadUserByLoginOpt(appConfig.getDefaultUserLogin());

        if (!userDetailsOpt.isPresent()) {
            final UserDetailsDmo userDetails = makeTechUserDmo();
            LOGGER.debug("Making the new tech user {}", userDetails);
            userComponent.insertUserRecord(userDetails);

        } else {
            LOGGER.debug("The tech user has already been created: {}", userDetailsOpt.get());
        }
    }

    private UserDetailsDmo makeTechUserDmo() {

        final UserDetailsDmo userDetails = new UserDetailsDmo();

        userDetails.setCode("TECH-USER");
        userDetails.setUserName("Nazimov-Aleksey-Igorevich");
        userDetails.setLogin(appConfig.getDefaultUserLogin());
        userDetails.setPassword(passwordEncoder.encode(appConfig.getDefaultUserPassword()));
        userDetails.setAuthorities(singletonList(new GrantedAuthorityDmo(USER_AUTHORITY)));

        userDetails.setEnabled(true);
        userDetails.setAccountExpired(false);
        userDetails.setAccountLocked(false);
        userDetails.setCredentialsExpired(false);

        return userDetails;
    }
}
