package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.FileStorageUpdaterDto;
import org.springframework.web.bind.annotation.PutMapping;

import static org.numamo.category.file.info.service.config.SecurityConfig.USER_API_PREFIX;

public interface StorageApi {

    @PutMapping(USER_API_PREFIX + "/make/updates")
    FileStorageUpdaterDto makeUpdates();

}
