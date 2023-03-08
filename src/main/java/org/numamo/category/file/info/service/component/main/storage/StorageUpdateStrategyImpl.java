package org.numamo.category.file.info.service.component.main.storage;

import org.numamo.category.file.info.service.component.api.main.session.FileSysIndexComponent;
import org.numamo.category.file.info.service.component.api.main.session.UserSessionManager;
import org.numamo.category.file.info.service.component.api.main.storage.StorageCleaner;
import org.numamo.category.file.info.service.component.api.main.storage.StorageUpdateStrategy;
import org.numamo.category.file.info.service.component.api.main.storage.StorageUpdater;
import org.numamo.category.file.info.service.controller.dto.FileStorageUpdaterDto;
import org.numamo.category.file.info.service.controller.dto.FileStorageUpdaterStateDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.time.Instant.now;
import static org.numamo.category.file.info.service.controller.dto.FileStorageUpdaterStateDto.*;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public class StorageUpdateStrategyImpl implements StorageUpdateStrategy {

    private static final Logger LOGGER = getLogger(StorageUpdateStrategyImpl.class);

    private final UserSessionManager userSessionManager;
    private final FileSysIndexComponent fileSysIndexComponent;
    private final StorageCleaner storageCleaner;
    private final StorageUpdater storageUpdater;

    @Autowired
    public StorageUpdateStrategyImpl(
            UserSessionManager userSessionManager,
            FileSysIndexComponent fileSysIndexComponent,
            StorageCleaner storageCleaner,
            StorageUpdater storageUpdater
    ) {
        this.userSessionManager = userSessionManager;
        this.fileSysIndexComponent = fileSysIndexComponent;
        this.storageCleaner = storageCleaner;
        this.storageUpdater = storageUpdater;
    }

    @Override
    public FileStorageUpdaterDto execute() {
        if (!userSessionManager.hasActiveUserSessions()) {
            final Optional<Long> idOpt = fileSysIndexComponent.getRequestedFileSysIndex();
            if (idOpt.isPresent()) {
                final long id = idOpt.get();
                try {
                    storageCleaner.cleanPrevRecords();
                    storageUpdater.update(id);
                } catch (Exception e) {
                    LOGGER.error("Storage update error:", e);
                    fileSysIndexComponent.removeRequestedIndex(id);
                    return make(FAILED);
                }
                fileSysIndexComponent.finalizeRequestedIndex(id);
                LOGGER.debug("Successfully updated storage by ID={}", id);
                return make(EXECUTED);
            } else {
                LOGGER.warn("Storage updating has already been started!");
                return make(EXECUTING);
            }
        } else {
            LOGGER.warn("There are some active user sessions. Let's wait!");
            return make(BLOCK_BY_USER);
        }
    }

    private FileStorageUpdaterDto make(
            final FileStorageUpdaterStateDto state
    ) {
        final FileStorageUpdaterDto dto = new FileStorageUpdaterDto();
        dto.setDate(now());
        dto.setState(state);
        return dto;
    }

}
