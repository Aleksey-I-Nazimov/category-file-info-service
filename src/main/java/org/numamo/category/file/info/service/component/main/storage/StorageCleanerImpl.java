package org.numamo.category.file.info.service.component.main.storage;

import org.numamo.category.file.info.service.component.api.main.storage.StorageCleaner;
import org.numamo.category.file.info.service.repository.api.CategoryRepository;
import org.numamo.category.file.info.service.repository.api.FileAccessRepository;
import org.numamo.category.file.info.service.repository.api.FileRepository;
import org.numamo.category.file.info.service.repository.api.FolderRepository;
import org.numamo.category.file.info.service.repository.api.user.UserRecordRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.NESTED;


@Component
public class StorageCleanerImpl implements StorageCleaner {

    private static final Logger LOGGER = getLogger(StorageCleanerImpl.class);

    private final CategoryRepository categoryRepository;
    private final FileAccessRepository fileAccessRepository;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final UserRecordRepository userRecordRepository;

    @Autowired
    public StorageCleanerImpl(
            CategoryRepository categoryRepository,
            FileAccessRepository fileAccessRepository,
            FileRepository fileRepository,
            FolderRepository folderRepository,
            UserRecordRepository userRecordRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.fileAccessRepository = fileAccessRepository;
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.userRecordRepository = userRecordRepository;
    }


    @Override
    @Transactional(propagation = NESTED, isolation = READ_COMMITTED)
    public void cleanPrevRecords() {

        LOGGER.info("Requesting storage clean");

        fileAccessRepository.deleteAll();
        fileRepository.deleteAll();
        folderRepository.deleteAll();
        categoryRepository.deleteAll();

        //  Users are not dropped!!! It's assumed

        LOGGER.trace("Storage was cleaned completely");
    }

}
