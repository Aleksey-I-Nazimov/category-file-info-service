package org.numamo.category.file.info.service.controller;

import org.numamo.category.file.info.service.component.api.main.storage.StorageUpdateStrategy;
import org.numamo.category.file.info.service.controller.api.StorageApi;
import org.numamo.category.file.info.service.controller.dto.FileStorageUpdaterDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;


@RestController
public class StorageApiImpl implements StorageApi {

    private static final Logger LOGGER = getLogger(StorageApiImpl.class);

    private final StorageUpdateStrategy storageUpdateStrategy;

    @Autowired
    public StorageApiImpl(StorageUpdateStrategy storageUpdateStrategy) {
        this.storageUpdateStrategy = storageUpdateStrategy;
    }

    @Override
    public FileStorageUpdaterDto makeUpdates() {
        LOGGER.info("Request for making updates!");
        final FileStorageUpdaterDto updater = storageUpdateStrategy.execute();
        LOGGER.info("Request for making updates was completed: {}",updater);
        return updater;
    }

}
