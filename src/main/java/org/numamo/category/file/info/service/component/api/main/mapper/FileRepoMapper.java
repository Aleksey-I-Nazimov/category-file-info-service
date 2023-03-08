package org.numamo.category.file.info.service.component.api.main.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.numamo.category.file.info.service.component.api.category.model.FileObjectDmo;
import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.FileAccessEntity;
import org.numamo.category.file.info.service.repository.entity.FileEntity;
import org.numamo.category.file.info.service.repository.entity.FolderEntity;
import org.numamo.category.file.info.service.repository.entity.dictionary.FileAccessDescriptorEntity;
import org.numamo.category.file.info.service.repository.entity.dictionary.FileExtensionEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.numamo.category.file.info.service.repository.entity.user.UserRecordEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring")
public interface FileRepoMapper {


    List<FolderDto> makeFolders(List<FolderEntity> folderEntities);

    @Mapping(target = "key", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "indexNumber", source = "fileSysIndex.number")
    FolderDto makeFolder(FolderEntity folderEntity);

    List<FileDto> makeFiles(List<FileEntity> fileEntities);


    @Mapping(target = "key", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "indexNumber", source = "fileSysIndex.number")
    @Mapping(target = "extensionCode", source = "fileExtension.code")
    @Mapping(target = "folders", ignore = true)
    FileDto makeFile(FileEntity fileEntity);

    @AfterMapping
    default void mapFile(FileEntity fileEntity, @MappingTarget FileDto fileDto) {
        final List<String> folderNames = new ArrayList<>();
        collectParents(fileEntity.getFolder(), folderNames);
        fileDto.setFolders(folderNames);
    }

    default void collectParents(FolderEntity folder, List<String> folderNames) {
        if (nonNull(folder)) {
            folderNames.add(folder.getName());
            collectParents(folder.getParent(), folderNames);
        }
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "fileObjectDmo.name")
    @Mapping(target = "fullPath", source = "fullPath")
    @Mapping(target = "byteSize", source = "byteSize")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "folder", source = "folder")
    @Mapping(target = "fileExtension", source = "extension")
    @Mapping(target = "fileSysIndex", source = "fileSysIndex")
    @Mapping(target = "fileAccessList", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FileEntity mapFileEntity(
            long id,
            FileObjectDmo fileObjectDmo,
            String fullPath,
            long byteSize,
            CategoryEntity category,
            FolderEntity folder,
            FileExtensionEntity extension,
            FileSysIndexEntity fileSysIndex
    );

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "fileObjectDmo.name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "fileSysIndex", source = "fileSysIndex")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FolderEntity mapFolderEntity(
            long id,
            FileObjectDmo fileObjectDmo,
            CategoryEntity category,
            FolderEntity parent,
            FileSysIndexEntity fileSysIndex
    );

    @Mapping(target = "id", source = "id")
    @Mapping(target = "file", source = "file")
    @Mapping(target = "fileAccessDescriptor", source = "descriptor")
    @Mapping(target = "userRecord", source = "userRecord")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FileAccessEntity mapFileAccessEntity(
            long id,
            FileEntity file,
            FileAccessDescriptorEntity descriptor,
            UserRecordEntity userRecord
    );

}
