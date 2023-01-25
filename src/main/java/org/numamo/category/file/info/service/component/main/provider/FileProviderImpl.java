package org.numamo.category.file.info.service.component.main.provider;

import org.numamo.category.file.info.service.component.api.main.provider.FileProvider;
import org.numamo.category.file.info.service.component.api.main.mapper.FileRepoMapper;
import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;
import org.numamo.category.file.info.service.repository.api.CategoryRepository;
import org.numamo.category.file.info.service.repository.api.FileRepository;
import org.numamo.category.file.info.service.repository.api.FolderRepository;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.FileEntity;
import org.numamo.category.file.info.service.repository.entity.FolderEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@Component
@Transactional
public class FileProviderImpl implements FileProvider {

    private static final Logger LOGGER = getLogger(FileProviderImpl.class);

    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final FileRepoMapper fileRepoMapper;

    @Autowired
    public FileProviderImpl(
            CategoryRepository categoryRepository,
            FileRepository fileRepository,
            FolderRepository folderRepository,
            FileRepoMapper fileRepoMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.fileRepoMapper = fileRepoMapper;
    }

    @Override
    public List<FolderDto> findRootFolders( final String categoryCode) {

        LOGGER.debug("Requesting root folders by code: {}",categoryCode);

        final CategoryEntity category = findCategory(categoryCode);
        final List<FolderEntity> folderEntities = folderRepository.findAllByCategoryAndParent(category,null);

        final List<FolderDto> folderDtoList = fileRepoMapper.makeFolders(folderEntities);
        LOGGER.debug("Found folders {} by code {}",folderDtoList,categoryCode);
        return folderDtoList;
    }

    @Override
    public List<FolderDto> findFolders(
            final String parentFolderName,
            final String categoryCode
    ) {

        LOGGER.debug("Requesting folders...");

        final CategoryEntity categoryEntity = findCategory(categoryCode);
        final FolderEntity parentFolderEntity = findParent(parentFolderName,categoryEntity);

        final List<FolderEntity> folderEntityList = folderRepository.findAllByCategoryAndParent(categoryEntity,parentFolderEntity);

        final List<FolderDto> folderDtoList = fileRepoMapper.makeFolders(folderEntityList);
        LOGGER.debug("Found folders {} by parent = {} and category = {}",folderDtoList,parentFolderEntity,categoryEntity);
        return folderDtoList;
    }

    @Override
    public List<FileDto> findRootFiles(final String categoryCode) {

        LOGGER.debug("Requesting root files by code: {}",categoryCode);

        final CategoryEntity categoryEntity = findCategory(categoryCode);

        final List<FileEntity> fileEntityList = fileRepository.findAllByCategoryAndFolder(categoryEntity,null);

        final List<FileDto> fileDtoList = fileRepoMapper.makeFiles(fileEntityList);
        LOGGER.debug("Found files {} by code {}",fileDtoList,categoryCode);
        return fileDtoList;
    }

    @Override
    public List<FileDto> findFiles(
            final String parentFolderName,
            final String categoryCode
    ) {

        LOGGER.debug("Requesting files...");

        final CategoryEntity categoryEntity = findCategory(categoryCode);
        final FolderEntity parentFolderEntity = findParent(parentFolderName,categoryEntity);

        final List<FileEntity> fileEntityList = fileRepository.findAllByCategoryAndFolder(categoryEntity,parentFolderEntity);

        final List<FileDto> fileDtoList = fileRepoMapper.makeFiles(fileEntityList);
        LOGGER.debug("Found files {} by parent = {} and category = {} ",fileDtoList,parentFolderEntity,categoryEntity);
        return fileDtoList;
    }


    private CategoryEntity findCategory (final String categoryCode) {
        return categoryRepository.findByCode(categoryCode).orElseThrow(()->
                new IllegalArgumentException("Category code was not found by code: "+categoryCode));
    }

    private FolderEntity findParent (
            final String parentFolderName,
            final CategoryEntity categoryEntity
    ) {
        return folderRepository.findByNameAndCategory(parentFolderName,categoryEntity).orElseThrow(()->
                new IllegalArgumentException("No parent was found by "+parentFolderName+" and category = "+categoryEntity));
    }
}
