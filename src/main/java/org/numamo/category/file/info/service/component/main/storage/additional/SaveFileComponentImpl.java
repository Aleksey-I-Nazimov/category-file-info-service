package org.numamo.category.file.info.service.component.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.FileObjectDmo;
import org.numamo.category.file.info.service.component.api.main.mapper.FileRepoMapper;
import org.numamo.category.file.info.service.component.api.main.storage.additional.FileAccessComponent;
import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.component.api.main.storage.additional.SaveFileComponent;
import org.numamo.category.file.info.service.repository.api.FileRepository;
import org.numamo.category.file.info.service.repository.api.FolderRepository;
import org.numamo.category.file.info.service.repository.api.dictionary.FileExtensionRepository;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.FileEntity;
import org.numamo.category.file.info.service.repository.entity.FolderEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class SaveFileComponentImpl implements SaveFileComponent {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(SaveFileComponentImpl.class);

    private final IdGenerator idGenerator;
    private final FileRepoMapper fileRepoMapper;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final FileExtensionRepository fileExtensionRepository;
    private final FileAccessComponent fileAccessComponent;

    @Autowired
    public SaveFileComponentImpl(
            IdGenerator idGenerator,
            FileRepoMapper fileRepoMapper,
            FileRepository fileRepository,
            FolderRepository folderRepository,
            FileExtensionRepository fileExtensionRepository,
            FileAccessComponent fileAccessComponent
    ) {
        this.idGenerator = idGenerator;
        this.fileRepoMapper = fileRepoMapper;
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.fileExtensionRepository = fileExtensionRepository;
        this.fileAccessComponent = fileAccessComponent;
    }


    // Public API:-----------------------------------------------------------------------
    @Override
    @Transactional
    public void saveCategoryFilesAndFolders(
            final FileObjectDmo fileObjectDmo,
            final CategoryEntity mainCategory,
            final FileSysIndexEntity fileSysIndex
    ) {
        LOGGER.trace("Requesting for storing the file object {}",fileObjectDmo);
        saveCategoryFilesAndFolders(fileObjectDmo,null,mainCategory,fileSysIndex);
    }



    // Internal methods:-----------------------------------------------------------------
    private void saveCategoryFilesAndFolders(
            final FileObjectDmo fileObjectDmo,
            final FolderEntity parentFolder,
            final CategoryEntity mainCategory,
            final FileSysIndexEntity fileSysIndex
    ) {
        if (fileObjectDmo.isFile()) {
            saveCategoryFiles(fileObjectDmo, parentFolder, mainCategory, fileSysIndex);
        } else {
            final FolderEntity subFolderEntity = saveCategoryFolders(fileObjectDmo, parentFolder, mainCategory, fileSysIndex);
            for (final FileObjectDmo childFileObject : fileObjectDmo.getChildList()) {
                saveCategoryFilesAndFolders(childFileObject, subFolderEntity, mainCategory, fileSysIndex);
            }
        }

    }

    private void saveCategoryFiles(
            final FileObjectDmo fileObjectDmo,
            final FolderEntity parentFolder,
            final CategoryEntity mainCategory,
            final FileSysIndexEntity fileSysIndex
    ) {
        final FileEntity fileEntity = fileRepoMapper.mapFileEntity(
                idGenerator.nextId(),
                fileObjectDmo,
                fileObjectDmo.getFullPath(),
                fileObjectDmo.getByteSize(),
                mainCategory,
                parentFolder,
                fileExtensionRepository.findByCode(fileObjectDmo.getExtension().toLowerCase()).orElseThrow(
                        () -> new IllegalArgumentException("File extension is not found by " + fileObjectDmo.getExtension())),
                fileSysIndex);
        fileEntity.setFileAccessList(fileAccessComponent.makeAccessEntity(fileObjectDmo, fileEntity));
        LOGGER.trace("The following file was constructed: {}", fileEntity);
        fileRepository.save(fileEntity);
    }

    private FolderEntity saveCategoryFolders(
            final FileObjectDmo fileObjectDmo,
            final FolderEntity parentFolder,
            final CategoryEntity mainCategory,
            final FileSysIndexEntity fileSysIndex
    ) {
        final FolderEntity folderEntity = fileRepoMapper.mapFolderEntity(
                idGenerator.nextId(),
                fileObjectDmo,
                mainCategory,
                parentFolder,
                fileSysIndex
        );

        LOGGER.trace("The following folder was constructed: {}", folderEntity);
        return folderRepository.save(folderEntity);
    }

}
