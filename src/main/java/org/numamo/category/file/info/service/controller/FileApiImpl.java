package org.numamo.category.file.info.service.controller;

import org.numamo.category.file.info.service.component.api.main.provider.FileProvider;
import org.numamo.category.file.info.service.controller.api.FileApi;
import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FileProviderRqDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@RestController
public class FileApiImpl implements FileApi {

    private static final Logger LOGGER = getLogger(FileApiImpl.class);

    private final FileProvider fileProvider;

    @Autowired
    public FileApiImpl(FileProvider fileProvider) {
        this.fileProvider = fileProvider;
    }

    @Override
    public List<FolderDto> findRootFolders(FileProviderRqDto fileProviderRq) {
        LOGGER.info("Requesting root folders: {}",fileProviderRq);
        final List<FolderDto> folders = fileProvider.findRootFolders(fileProviderRq.getCategoryCode());
        LOGGER.info("Found root folders: {} by {}",folders,fileProviderRq);
        return folders;
    }

    @Override
    public List<FolderDto> findFolders(FileProviderRqDto fileProviderRq) {
        LOGGER.info("Requesting folders: {}",fileProviderRq);
        final List<FolderDto> folders = fileProvider.findFolders(fileProviderRq.getParentFolderName(),fileProviderRq.getCategoryCode());
        LOGGER.info("Found folders: {} by {}",folders,fileProviderRq);
        return folders;
    }

    @Override
    public List<FileDto> findRootFiles(FileProviderRqDto fileProviderRq) {
        LOGGER.info("Requesting root files: {}",fileProviderRq);
        final List<FileDto> files = fileProvider.findRootFiles(fileProviderRq.getCategoryCode());
        LOGGER.info("Found root files: {} by {}",files, fileProviderRq);
        return files;
    }

    @Override
    public List<FileDto> findFiles(FileProviderRqDto fileProviderRq) {
        LOGGER.info("Requesting files: {}",fileProviderRq);
        final List<FileDto> files = fileProvider.findFiles(fileProviderRq.getParentFolderName(),fileProviderRq.getCategoryCode());
        LOGGER.info("Found files: {} by {}",files,fileProviderRq);
        return files;
    }
}
