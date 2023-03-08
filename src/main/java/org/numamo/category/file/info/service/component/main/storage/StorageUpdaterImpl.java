package org.numamo.category.file.info.service.component.main.storage;

import org.numamo.category.file.info.service.component.api.category.CategoryReader;
import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;
import org.numamo.category.file.info.service.component.api.main.storage.StorageUpdater;
import org.numamo.category.file.info.service.component.api.main.storage.additional.CategoriesComponent;
import org.numamo.category.file.info.service.component.api.main.storage.additional.SaveFileComponent;
import org.numamo.category.file.info.service.component.api.main.user.UserComponent;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexRepository;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;


@Component
public class StorageUpdaterImpl implements StorageUpdater {

    private static final Logger LOGGER = getLogger(StorageUpdaterImpl.class);

    private final CategoryReader categoryReader;
    private final UserComponent userComponent;
    private final FileSysIndexRepository fileSysIndexRepository;
    private final SaveFileComponent fileComponent;
    private final CategoriesComponent categoryComponent;

    @Autowired
    public StorageUpdaterImpl(
            CategoryReader categoryReader,
            UserComponent userComponent,
            FileSysIndexRepository fileSysIndexRepository,
            SaveFileComponent fileComponent,
            CategoriesComponent categoryComponent
    ) {
        this.categoryReader = categoryReader;
        this.userComponent = userComponent;
        this.fileSysIndexRepository = fileSysIndexRepository;
        this.fileComponent = fileComponent;
        this.categoryComponent = categoryComponent;
    }

    @Override
    @Transactional(isolation = READ_COMMITTED)
    public void update(final long fileSysIndexId) {

        // Searching REQUESTED index:--------------------------------
        LOGGER.debug("Requesting update by file sys index: {}", fileSysIndexId);
        final FileSysIndexEntity fileSysIndex = fileSysIndexRepository
                .findById(fileSysIndexId)
                .orElseThrow(() ->
                        new IllegalArgumentException("The requested index was not found by key=" + fileSysIndexId));

        // Reading the category model:-------------------------------
        final List<CategoryDmo> categories = categoryReader.read();

        // Updating users for files:---------------------------------
        userComponent.checkAndInsertNewRecords(
                categories
                        .stream()
                        .flatMap(c -> c.getFileObject().getUserAccessList().stream())
                        .collect(toList())
        );

        // Updating files from categories:---------------------------
        for (final CategoryDmo category : categories) {
            final CategoryEntity categoryEntity = categoryComponent.save(category);
            fileComponent.saveCategoryFilesAndFolders(category.getFileObject(), categoryEntity, fileSysIndex);
        }

        LOGGER.trace("Update was completed: {}", categories);
    }


}
