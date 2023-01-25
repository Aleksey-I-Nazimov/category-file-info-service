package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.UserSessionRqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserSessionApi {

    @PutMapping("/make/user-session")
    Long recreateSession (@RequestBody UserSessionRqDto userSessionRqDto);

}
