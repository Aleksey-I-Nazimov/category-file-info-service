package org.numamo.category.file.info.service.controller;

import org.numamo.category.file.info.service.component.api.main.provider.FileSysIndexServiceStatusProvider;
import org.numamo.category.file.info.service.controller.api.FileSysIndexServiceStatusApi;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class FileSysIndexServiceStatusApiImpl implements FileSysIndexServiceStatusApi {

    private static final Logger LOGGER = getLogger(FileSysIndexServiceStatusApiImpl.class);

    private final FileSysIndexServiceStatusProvider fileSysIndexServiceStatusProvider;

    @Autowired
    public FileSysIndexServiceStatusApiImpl(
            FileSysIndexServiceStatusProvider fileSysIndexServiceStatusProvider
    ) {
        this.fileSysIndexServiceStatusProvider = fileSysIndexServiceStatusProvider;
    }

    @Override
    public ResponseEntity<Boolean> enableSysIndexStatus() {
        LOGGER.info("Request for enabling sys index state");
        this.fileSysIndexServiceStatusProvider.setEnabled(true);
        return isSysIndexEnabled();
    }

    @Override
    public ResponseEntity<Boolean> disableSysIndexStatus() {
        LOGGER.info("Request for disabling sys index state");
        this.fileSysIndexServiceStatusProvider.setEnabled(false);
        return isSysIndexEnabled();
    }

    @Override
    public ResponseEntity<Boolean> isSysIndexEnabled() {
        LOGGER.info("Request for sys index state");
        final boolean result = fileSysIndexServiceStatusProvider.isEnabled();
        LOGGER.info("Request for disabling sys index state: {}",result);
        return ok(result);
    }

}
