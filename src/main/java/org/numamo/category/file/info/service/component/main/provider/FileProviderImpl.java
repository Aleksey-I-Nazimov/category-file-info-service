package org.numamo.category.file.info.service.component.main.provider;

import org.numamo.category.file.info.service.component.api.main.mapper.FileRepoMapper;
import org.numamo.category.file.info.service.component.api.main.provider.FileProvider;
import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;
import org.numamo.category.file.info.service.repository.api.CategoryRepository;
import org.numamo.category.file.info.service.repository.api.FileRepository;
import org.numamo.category.file.info.service.repository.api.FolderRepository;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexEditor;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.FileEntity;
import org.numamo.category.file.info.service.repository.entity.FolderEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;


@Component
public class FileProviderImpl implements FileProvider {

    private static final Logger LOGGER = getLogger(FileProviderImpl.class);

    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final FileRepoMapper fileRepoMapper;
    private final FileSysIndexEditor fileSysIndexEditor;

    @Autowired
    public FileProviderImpl(
            CategoryRepository categoryRepository,
            FileRepository fileRepository,
            FolderRepository folderRepository,
            FileRepoMapper fileRepoMapper,
            FileSysIndexEditor fileSysIndexEditor
    ) {
        this.categoryRepository = categoryRepository;
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.fileRepoMapper = fileRepoMapper;
        this.fileSysIndexEditor = fileSysIndexEditor;
    }


    @Override
    @Transactional(isolation = READ_COMMITTED)
    public List<FolderDto> findRootFolders(
            final String categoryCode,
            final Long indexNumber
    ) {
        LOGGER.debug("Requesting root folders by code and index: {} {}", categoryCode, indexNumber);

        final CategoryEntity category = findCategory(categoryCode);
        final FileSysIndexEntity sysIndexEntity = checkIndex(indexNumber);

        final List<FolderEntity> folderEntities = folderRepository
                .findAllByCategoryAndParentAndFileSysIndex(category, null, sysIndexEntity);

        final List<FolderDto> folderDtoList = fileRepoMapper.makeFolders(folderEntities);
        LOGGER.debug("Found folders {} by code {}", folderDtoList, categoryCode);
        return folderDtoList;
    }

    @Override
    @Transactional(isolation = READ_COMMITTED)
    public List<FolderDto> findFolders(
            final Long parentKey,
            final Long indexNumber
    ) {
        LOGGER.debug("Requesting folders by parent and index: {}x{}", parentKey, indexNumber);

        final FileSysIndexEntity sysIndexEntity = checkIndex(indexNumber);
        final FolderEntity parentFolderEntity = findParent(parentKey, sysIndexEntity);

        final List<FolderEntity> folderEntityList = folderRepository.findAllByParentAndFileSysIndex(
                parentFolderEntity,
                sysIndexEntity
        );

        final List<FolderDto> folderDtoList = fileRepoMapper.makeFolders(folderEntityList);
        LOGGER.debug("Found folders {} by parent = {} and index = {}", folderDtoList, parentFolderEntity, sysIndexEntity);
        return folderDtoList;
    }

    @Override
    @Transactional(isolation = READ_COMMITTED)
    public List<FileDto> findFiles(
            final Long parentKey,
            final Long indexNumber
    ) {
        LOGGER.debug("Requesting files by parent {} and index {}", parentKey, indexNumber);

        final FileSysIndexEntity sysIndexEntity = checkIndex(indexNumber);
        final FolderEntity parentFolderEntity = findParent(parentKey, sysIndexEntity);

        final List<FileEntity> fileEntityList = fileRepository.findAllByFolderAndFileSysIndex(
                parentFolderEntity,
                sysIndexEntity
        );

        final List<FileDto> fileDtoList = fileRepoMapper.makeFiles(fileEntityList);
        LOGGER.debug("Found files {} by parent = {} and index = {} ", fileDtoList, parentFolderEntity, sysIndexEntity);
        return fileDtoList;
    }


    private FileSysIndexEntity checkIndex(Long indexNumber) {
        final FileSysIndexEntity sysIndexEntity = fileSysIndexEditor.findAppliedActualIndex()
                .orElseThrow(() -> new IllegalStateException("Actual index was not found"));

        if (Objects.equals(sysIndexEntity.getNumber(), indexNumber)) {
            LOGGER.info("The index was checked: {}", sysIndexEntity);
            return sysIndexEntity;
        } else {
            throw new IllegalArgumentException("The index number=" + indexNumber + " is not actual for " + sysIndexEntity);
        }
    }

    private CategoryEntity findCategory(final String categoryCode) {
        return categoryRepository.findByCode(categoryCode).orElseThrow(() ->
                new IllegalArgumentException("Category code was not found by code: " + categoryCode));
    }

    private FolderEntity findParent(
            final Long parentId,
            final FileSysIndexEntity fileSysIndexEntity
    ) {
        return folderRepository.findByIdAndFileSysIndex(parentId, fileSysIndexEntity).orElseThrow(() ->
                new IllegalArgumentException("No parent was found by id=" + parentId + " and index = " + fileSysIndexEntity));
    }

}
