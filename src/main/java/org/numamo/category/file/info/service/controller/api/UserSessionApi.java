package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.UserSessionRqDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.numamo.category.file.info.service.config.SecurityConfig.USER_API_PREFIX;

public interface UserSessionApi {

    @PutMapping(USER_API_PREFIX + "/make/user-session")
    Long recreateSession(@RequestBody UserSessionRqDto userSessionRqDto);

}
