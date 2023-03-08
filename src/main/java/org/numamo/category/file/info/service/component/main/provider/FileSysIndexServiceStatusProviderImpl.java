package org.numamo.category.file.info.service.component.main.provider;

import org.numamo.category.file.info.service.component.api.main.provider.FileSysIndexServiceStatusProvider;
import org.numamo.category.file.info.service.component.api.main.session.FileSysIndexServiceStatusComponent;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexServiceStatusEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;


@Component
public class FileSysIndexServiceStatusProviderImpl implements FileSysIndexServiceStatusProvider {

    private static final Logger LOGGER = getLogger(FileSysIndexServiceStatusProviderImpl.class);

    private final FileSysIndexServiceStatusComponent statusComponent;

    @Autowired
    public FileSysIndexServiceStatusProviderImpl(FileSysIndexServiceStatusComponent statusComponent) {
        this.statusComponent = statusComponent;
    }


    @Override
    @Transactional(isolation = READ_COMMITTED)
    public void setEnabled(boolean enabled) {
        statusComponent.changeStatus(enabled);
        LOGGER.trace("The new status was saved: {}", enabled);
    }

    @Override
    @Transactional(isolation = READ_COMMITTED)
    public boolean isEnabled() {

        final FileSysIndexServiceStatusEntity existedStatus = statusComponent.readActualStatus();
        final boolean flag = existedStatus.getEnabled();
        LOGGER.debug("Sys index service status is enabled: {}", flag);
        return flag;
    }

}
