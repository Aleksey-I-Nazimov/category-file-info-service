package org.numamo.category.file.info.service.component.main.session;

import org.numamo.category.file.info.service.component.api.main.session.FileSysIndexComponent;
import org.numamo.category.file.info.service.component.api.main.session.FileSysIndexServiceStatusComponent;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexEditor;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexRepository;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexServiceStatusEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.NESTED;

@Component
public class FileSysIndexComponentImpl implements FileSysIndexComponent {

    private static final Logger LOGGER = getLogger(FileSysIndexComponentImpl.class);

    private final FileSysIndexServiceStatusComponent fileSysIndexServiceStatusComponent;
    private final FileSysIndexRepository fileSysIndexRepository;
    private final FileSysIndexEditor fileSysIndexEditor;

    @Autowired
    public FileSysIndexComponentImpl(
            FileSysIndexServiceStatusComponent fileSysIndexServiceStatusComponent,
            FileSysIndexRepository fileSysIndexRepository,
            FileSysIndexEditor fileSysIndexEditor
    ) {
        this.fileSysIndexServiceStatusComponent = fileSysIndexServiceStatusComponent;
        this.fileSysIndexRepository = fileSysIndexRepository;
        this.fileSysIndexEditor = fileSysIndexEditor;
    }


    @Override
    @Transactional(propagation = NESTED, isolation = READ_COMMITTED)
    public Optional<Long> getRequestedFileSysIndex() {

        final FileSysIndexServiceStatusEntity indexStatus = fileSysIndexServiceStatusComponent.readActualStatus();

        if (indexStatus.getEnabled()) {
            return fileSysIndexEditor
                    .findRequestedIndex()
                    .map(
                            index -> {
                                LOGGER.info("The following index state was set as a requested: {}", index);
                                return index.getId();
                            }
                    );
        } else {
            LOGGER.warn("The file sys index request cannot be saved. It has disabled state!");
            return Optional.empty();
        }
    }

    @Override
    @Transactional(propagation = NESTED, isolation = READ_COMMITTED)
    public void finalizeRequestedIndex(long fileSysIndexId) {

        LOGGER.debug("The file system index finalization by ID = {}", fileSysIndexId);

        final FileSysIndexEntity fileSysIndex = fileSysIndexRepository
                .findById(fileSysIndexId)
                .orElseThrow(() ->
                        new IllegalArgumentException("The file sys index cannot be found by ID = " + fileSysIndexId)
                );

        fileSysIndexEditor.archiveActualIndex(fileSysIndex);
        LOGGER.debug("The file system index was finalized: {}", fileSysIndex);
    }
}
