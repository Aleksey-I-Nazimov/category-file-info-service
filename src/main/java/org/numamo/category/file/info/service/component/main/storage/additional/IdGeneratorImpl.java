package org.numamo.category.file.info.service.component.main.storage.additional;

import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class IdGeneratorImpl implements IdGenerator {

    private static final Logger LOGGER = getLogger(IdGeneratorImpl.class);

    @Override
    public long nextId() {
        final long id = UUID.randomUUID().getLeastSignificantBits();
        LOGGER.trace("Requested ID={}",id);
        return id;
    }
}
