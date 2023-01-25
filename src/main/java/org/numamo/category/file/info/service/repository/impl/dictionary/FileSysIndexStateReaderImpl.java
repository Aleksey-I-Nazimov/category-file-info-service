package org.numamo.category.file.info.service.repository.impl.dictionary;

import org.numamo.category.file.info.service.repository.api.dictionary.FileSysIndexStateReader;
import org.numamo.category.file.info.service.repository.api.dictionary.FileSysIndexStateRepository;
import org.numamo.category.file.info.service.repository.entity.dictionary.FileSysIndexStateEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class FileSysIndexStateReaderImpl implements FileSysIndexStateReader {

    public static final String REQUESTED = "REQUESTED";
    public static final String APPLIED = "APPLIED";
    public static final String ARCHIVED = "ARCHIVED";

    private static final Logger LOGGER  = getLogger(FileSysIndexStateReaderImpl.class);

    private final FileSysIndexStateRepository fileSysIndexStateRepository;

    @Autowired
    public FileSysIndexStateReaderImpl(FileSysIndexStateRepository fileSysIndexStateRepository) {
        this.fileSysIndexStateRepository = fileSysIndexStateRepository;
    }


    @Override
    public FileSysIndexStateEntity findRequestedState() {
        final FileSysIndexStateEntity state = fileSysIndexStateRepository.findByCode(REQUESTED).orElseThrow(()->
                new IllegalStateException("Cannot find REQUESTED state"));

        LOGGER.trace("The following REQUESTED state was read {}",state);
        return state;
    }

    @Override
    public FileSysIndexStateEntity findAppliedState() {
        final FileSysIndexStateEntity state =  fileSysIndexStateRepository.findByCode(APPLIED).orElseThrow(()->
                new IllegalStateException("Cannot find APPLIED state"));
        LOGGER.trace("The following APPLIED state was read {}",state);
        return state;
    }

    @Override
    public FileSysIndexStateEntity findArchivedState() {
        final FileSysIndexStateEntity state = fileSysIndexStateRepository.findByCode(ARCHIVED).orElseThrow(()->
                new IllegalStateException("Cannot find ARCHIVED state"));
        LOGGER.trace("The following ARCHIVED state was read {}",state);
        return state;
    }
}
