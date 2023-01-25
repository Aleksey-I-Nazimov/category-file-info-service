package org.numamo.category.file.info.service.component.main.session;

import org.numamo.category.file.info.service.component.api.main.session.FileSysIndexServiceStatusComponent;
import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexServiceStatusRepository;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexServiceStatusEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static java.lang.Thread.currentThread;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;


@Component
public class FileSysIndexServiceStatusComponentImpl implements FileSysIndexServiceStatusComponent {

    private static final Logger LOGGER = getLogger(FileSysIndexServiceStatusComponentImpl.class);

    private final FileSysIndexServiceStatusRepository statusRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public FileSysIndexServiceStatusComponentImpl(
            FileSysIndexServiceStatusRepository statusRepository,
            IdGenerator idGenerator
    ) {
        this.statusRepository = statusRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional(propagation=MANDATORY,isolation=READ_COMMITTED)
    public void changeStatus(
            final boolean enabled
    ) {
        final FileSysIndexServiceStatusEntity newStatus = makeNewStatus(enabled);
        statusRepository.save(newStatus);
        LOGGER.debug("The new status was saved: {}",newStatus);
    }

    @Override
    @Transactional(propagation=MANDATORY,isolation=READ_COMMITTED)
    public FileSysIndexServiceStatusEntity readActualStatus() {
        return readStatus();
    }

    private FileSysIndexServiceStatusEntity readStatus() {

        final FileSysIndexServiceStatusEntity existedStatus = statusRepository
                .findAllByActual(true)
                .stream()
                .findAny()
                .orElseGet(this::makeAndSaveInitialStatus);

        LOGGER.info("Read actual status: {}",existedStatus);

        return existedStatus;
    }

    private FileSysIndexServiceStatusEntity makeNewStatus (final boolean enabled) {

        final FileSysIndexServiceStatusEntity existedStatus = readStatus();

        final FileSysIndexServiceStatusEntity newStatus = new FileSysIndexServiceStatusEntity();
        newStatus.setId(idGenerator.nextId());
        newStatus.setInfo("The new index status: " + getThreadInfo());
        newStatus.setActual(true);
        newStatus.setEnabled(enabled);
        newStatus.setPrevFileSysIndexServiceStatus(existedStatus);

        /* Obvious closing the previous status */
        existedStatus.setActual(false);
        existedStatus.setInfo(existedStatus.getInfo()+" was closed by "+getThreadInfo());
        return newStatus;
    }

    private FileSysIndexServiceStatusEntity makeAndSaveInitialStatus () {
        final FileSysIndexServiceStatusEntity status = new FileSysIndexServiceStatusEntity();
        status.setId(idGenerator.nextId());
        status.setInfo("The initial sys index status entity: " + getThreadInfo());
        status.setActual(true);
        status.setEnabled(true);
        status.setPrevFileSysIndexServiceStatus(null);
        statusRepository.save(status);
        LOGGER.info("Made initial status: {}", status);
        return status;
    }

    private String getThreadInfo() {
        return String.valueOf(Instant.now()) +
                " thread info: " +
                " name= " +
                currentThread().getName() +
                " id= " +
                currentThread().getId();
    }

}
