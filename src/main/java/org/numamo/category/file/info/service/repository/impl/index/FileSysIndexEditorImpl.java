package org.numamo.category.file.info.service.repository.impl.index;

import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.repository.api.dictionary.FileSysIndexStateReader;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexEditor;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexRepository;
import org.numamo.category.file.info.service.repository.api.index.exception.ArchivedIndexIllegalStateException;
import org.numamo.category.file.info.service.repository.entity.dictionary.FileSysIndexStateEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;


@Component
public class FileSysIndexEditorImpl implements FileSysIndexEditor {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(FileSysIndexEditorImpl.class);

    private static final long ROOT_INDEX_PRIMARY_KEY = 0;
    private static final long ROOT_INDEX_NUMBER = 1;

    private final FileSysIndexStateReader fileSysIndexStateReader;
    private final FileSysIndexRepository fileSysIndexRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public FileSysIndexEditorImpl(
            FileSysIndexStateReader fileSysIndexStateReader,
            FileSysIndexRepository fileSysIndexRepository,
            IdGenerator idGenerator
    ) {
        this.fileSysIndexStateReader = fileSysIndexStateReader;
        this.fileSysIndexRepository = fileSysIndexRepository;
        this.idGenerator = idGenerator;
    }


    // Public API:-----------------------------------------------------------------------
    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public Optional<FileSysIndexEntity> findRequestedIndex() {

        final FileSysIndexStateEntity requestedState = fileSysIndexStateReader.findRequestedState();
        final List<FileSysIndexEntity> requestedIndexes = fileSysIndexRepository.findAllByFileSysIndexState(requestedState);
        final FileSysIndexEntity actualIndex = findAppliedActualIndex()
                .orElseThrow(() -> new IllegalStateException("The applied actual index was not found!"));

        if (requestedIndexes.isEmpty()) {
            return Optional.of(makeAndSaveRequestedIndex(actualIndex, requestedState));

        } else if (requestedIndexes.size() > 1) {
            throw new IllegalStateException("Multiple 'requested' indexes: " + requestedIndexes);

        } else {
            LOGGER.warn("The requested index has already been created: {}", requestedIndexes);
            return Optional.empty();
        }
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public Optional<FileSysIndexEntity> findAppliedActualIndex() {

        final FileSysIndexStateEntity actualAppliedState = fileSysIndexStateReader
                .findAppliedState();
        final List<FileSysIndexEntity> actualIndexList = fileSysIndexRepository
                .findAllByFileSysIndexState(actualAppliedState);

        if (actualIndexList.isEmpty()) {
            return Optional.empty();

        } else if (actualIndexList.size() > 1) {
            throw new IllegalStateException("Multiple 'applied' indexes: " + actualIndexList);

        } else {
            final FileSysIndexEntity index = actualIndexList.get(0);
            LOGGER.debug("The following actual index was read {}", index);
            return Optional.of(index);
        }
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public void makeRootActualIndex() {

        final Optional<FileSysIndexEntity> rootOpt = fileSysIndexRepository.findById(ROOT_INDEX_PRIMARY_KEY);

        if (rootOpt.isPresent()) {
            LOGGER.warn("The root index was created: {}", rootOpt);
        } else {
            makeAndSaveRootIndex();
        }
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public void archiveActualIndex(FileSysIndexEntity newIndex) throws ArchivedIndexIllegalStateException {

        final FileSysIndexEntity parentIndex = requireNonNull(newIndex.getPrevFileSysIndex(),
                "The parent index was skipped");

        if (parentIndex.getFileSysIndexState().isApplied() &&
                newIndex.getFileSysIndexState().isRequested()) {

            final FileSysIndexStateEntity appliedState = fileSysIndexStateReader.findAppliedState();
            final FileSysIndexStateEntity archivedState = fileSysIndexStateReader.findArchivedState();

            // Updating parent info:-------------
            parentIndex.setFileSysIndexState(archivedState);
            parentIndex.setInfo(parentIndex.getInfo() + " was archived at " + Instant.now());

            // Updating new index:---------------
            newIndex.setFileSysIndexState(appliedState);
            newIndex.setInfo(newIndex.getInfo() + " was applied at " + Instant.now());

            fileSysIndexRepository.save(newIndex);
            LOGGER.debug("The new index was saved {}", newIndex);

        } else {
            throw new ArchivedIndexIllegalStateException(newIndex);
        }
    }


    // Internal methods:-----------------------------------------------------------------
    private FileSysIndexEntity makeAndSaveRequestedIndex(
            final FileSysIndexEntity actualIndex,
            final FileSysIndexStateEntity requestedState
    ) {
        final FileSysIndexEntity index = new FileSysIndexEntity();
        index.setId(nextId(false));
        index.setInfo("The new requested index: " + Instant.now() + " ");
        index.setFileSysIndexState(requestedState);
        index.setPrevFileSysIndex(actualIndex);
        index.setNumber(actualIndex.getNumber() + 1);

        fileSysIndexRepository.save(index);

        LOGGER.debug("The 'requested' index was created and saved: {}", index);
        return index;
    }

    private void makeAndSaveRootIndex() {

        final FileSysIndexEntity index = new FileSysIndexEntity();
        index.setId(nextId(true));
        index.setInfo("The new ROOT index: " + Instant.now() + " ");
        index.setFileSysIndexState(fileSysIndexStateReader.findAppliedState());
        index.setPrevFileSysIndex(null);
        index.setNumber(ROOT_INDEX_NUMBER);

        fileSysIndexRepository.save(index);

        LOGGER.debug("The ROOT was created and saved: {}", index);
    }

    private long nextId(boolean isRoot) {

        long id = ROOT_INDEX_PRIMARY_KEY;

        while (!isRoot && id == ROOT_INDEX_PRIMARY_KEY) {
            id = idGenerator.nextId();
        }
        LOGGER.trace("Selected ID = {}", id);
        return id;
    }

}
