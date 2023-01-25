package org.numamo.category.file.info.service.component.api.main.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.numamo.category.file.info.service.repository.entity.FileSessionEntity;
import org.numamo.category.file.info.service.repository.entity.UserRecordEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;


@Mapper(componentModel = "spring")
public interface UserSessionMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "userRecord", source = "userRecord")
    @Mapping(target = "fileSysIndex", source = "fileSysIndex")
    @Mapping(target = "info", constant = "the new file session created by request")
    FileSessionEntity makeNewFileSessionEntity(
            long id,
            UserRecordEntity userRecord,
            FileSysIndexEntity fileSysIndex
    );

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "login", source = "login")
    @Mapping(target = "code", source = "code")
    UserRecordEntity makeNewUserRecord(
            long id,
            String login,
            String code
    );


}
