package org.numamo.category.file.info.service.config;

import org.numamo.category.file.info.service.repository.api.index.FileSysIndexEditor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class SpringStartEventConfigure implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOGGER = getLogger(SpringStartEventConfigure.class);

    private final FileSysIndexEditor fileSysIndexEditor;

    @Autowired
    public SpringStartEventConfigure(FileSysIndexEditor fileSysIndexEditor) {
        this.fileSysIndexEditor = fileSysIndexEditor;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void onApplicationEvent(ApplicationStartedEvent event) {

        LOGGER.info("The application start event: creating actual index");

        try {
            fileSysIndexEditor.makeRootActualIndex();
        } catch (Exception e) {
            LOGGER.error("Caught exception: Possibly the root actual index has already been created: ", e);
        }

    }
}
