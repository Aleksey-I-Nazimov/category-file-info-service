package org.numamo.category.file.info.service.controller;

import org.numamo.category.file.info.service.component.api.main.provider.FileProvider;
import org.numamo.category.file.info.service.controller.api.FileApi;
import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;
import org.numamo.category.file.info.service.controller.dto.ProviderRqDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Long.parseLong;
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
    public List<FolderDto> findRootFolders(final ProviderRqDto providerRequest) {
        LOGGER.info("Requesting root folders: {}", providerRequest);
        final List<FolderDto> folders = fileProvider.findRootFolders(
                providerRequest.getCategoryCode(),
                parseLong(providerRequest.getParentIndexNumber())
        );
        LOGGER.info("Found root folders: {} by {}", folders, providerRequest);
        return folders;
    }

    @Override
    public List<FolderDto> findFolders(final ProviderRqDto providerRequest) {
        LOGGER.info("Requesting folders: {}", providerRequest);
        final List<FolderDto> folders = fileProvider.findFolders(
                parseLong(providerRequest.getParentKey()),
                parseLong(providerRequest.getParentIndexNumber())
        );
        LOGGER.info("Found folders: {} by {}", folders, providerRequest);
        return folders;
    }

    @Override
    public List<FileDto> findFiles(final ProviderRqDto providerRequest) {
        LOGGER.info("Requesting files: {}", providerRequest);
        final List<FileDto> files = fileProvider.findFiles(
                parseLong(providerRequest.getParentKey()),
                parseLong(providerRequest.getParentIndexNumber())
        );
        LOGGER.info("Found files: {} by {}", files, providerRequest);
        return files;
    }
}
