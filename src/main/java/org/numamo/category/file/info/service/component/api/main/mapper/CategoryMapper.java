package org.numamo.category.file.info.service.component.api.main.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;
import org.numamo.category.file.info.service.controller.dto.CategoryDto;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;


@Mapper(componentModel = "spring")
public interface CategoryMapper {


    @Mapping(target = "code", source = "category.code")
    @Mapping(target = "name", source = "category.name")
    @Mapping(target = "indexNumber", source = "fileSysIndex.number")
    CategoryDto make(
            CategoryEntity category,
            FileSysIndexEntity fileSysIndex
    );


    @Mapping(target = "code", source = "category.index.code")
    @Mapping(target = "name", source = "category.index.name")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CategoryEntity makeByDmo(
            CategoryDmo category,
            long id
    );

}
