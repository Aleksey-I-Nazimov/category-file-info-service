package org.numamo.category.file.info.service.component.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.numamo.category.file.info.service.component.api.category.MetadataFileReader;
import org.numamo.category.file.info.service.component.api.category.model.CodeDmo;
import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class MetadataFileReaderImpl implements MetadataFileReader {

    private static final Logger LOGGER = getLogger(MetadataFileReaderImpl.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<CodeDmo> readCategories(File categoryFile) {
        try {
            final List<CodeDmo> categories = Stream
                    .of(mapper.readValue(categoryFile,CodeDmo[].class))
                    .collect(toList());
            LOGGER.debug("Read category codes: {} from {}",categories,categoryFile);
            return categories;
        } catch (Exception e) {
            throw new IllegalStateException("The input category metadata file cannot be read: " + categoryFile, e);
        }
    }

    @Override
    public List<UserAccessDmo> readAccesses(File accessFile) {
        try {
            final List<UserAccessDmo> accessList = Stream
                    .of(mapper.readValue(accessFile, UserAccessDmo[].class))
                    .collect(toList());
            LOGGER.debug("Read user access list: {} from {}",accessList,accessFile);
            return accessList;
        } catch (Exception e) {
            throw new IllegalArgumentException("The input access metadata file cannot be read: "+accessFile);
        }
    }
}
