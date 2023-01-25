package org.numamo.category.file.info.service.component.api.main.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;
import org.numamo.category.file.info.service.controller.dto.CategoryDto;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    List<CategoryDto> make (List<CategoryEntity> categoryList);

    @Mapping(target="code",source="code")
    @Mapping(target="name",source="name")
    CategoryDto make (CategoryEntity category);


    @Mapping(target="code",source="category.index.code")
    @Mapping(target="name",source="category.index.name")
    @Mapping(target="id",source ="id")
    @Mapping(target="createdAt",ignore=true)
    @Mapping(target="updatedAt",ignore=true)
    CategoryEntity makeByDmo (CategoryDmo category,long id);

}
