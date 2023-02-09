package org.numamo.category.file.info.service.controller;


import org.numamo.category.file.info.service.component.api.main.provider.UserSessionProvider;
import org.numamo.category.file.info.service.controller.api.UserSessionApi;
import org.numamo.category.file.info.service.controller.dto.UserSessionRqDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Long.parseLong;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class UserSessionApiImpl implements UserSessionApi {

    private static final Logger LOGGER = getLogger(UserSessionApiImpl.class);

    private final UserSessionProvider userSessionProvider;

    @Autowired
    public UserSessionApiImpl(UserSessionProvider userSessionProvider) {
        this.userSessionProvider = userSessionProvider;
    }

    @Override
    public Long recreateSession(UserSessionRqDto userSessionRqDto) {

        LOGGER.info ("Request for recreating user session: {}",userSessionRqDto);

        final long sessionId = userSessionProvider
                .recreateSession(userSessionRqDto.getUserLogin(), parseLong(userSessionRqDto.getExistedSessionId()));

        LOGGER.info("The user session was made with ID = {}",sessionId);
        return sessionId;
    }
}
