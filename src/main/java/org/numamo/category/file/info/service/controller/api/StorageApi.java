package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.FileStorageUpdaterDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface StorageApi {

    @PutMapping("/make/updates")
    FileStorageUpdaterDto makeUpdates();

}
