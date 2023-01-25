package org.numamo.category.file.info.service.component.category.file;

import org.numamo.category.file.info.service.component.api.category.file.FileComponent;
import org.numamo.category.file.info.service.config.api.AppConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class FileComponentImpl implements FileComponent {

    private static final Logger LOGGER = getLogger (FileComponentImpl.class);

    private final AppConfig appConfig;

    @Autowired
    public FileComponentImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public boolean isCategoryFile(File anyFile) {
        final boolean flag = appConfig.getCategoryFileName().equals(anyFile.getName());
        LOGGER.trace("The category file was detected {} in {} ", flag, anyFile);
        return flag;
    }

    @Override
    public boolean isUserAccessFile(File anyFile) {
        final boolean flag = appConfig.getUserAccessFileName().equals(anyFile.getName());
        LOGGER.trace("The user access file name was detected {} in {}", flag, anyFile);
        return flag;
    }

    @Override
    public List<File> getSubFiles(File fileRoot) {
        File[] fileArray = fileRoot.listFiles();
        fileArray = nonNull(fileArray) ? fileArray : new File[]{};
        return Stream.of(fileArray).collect(toList());
    }


}
