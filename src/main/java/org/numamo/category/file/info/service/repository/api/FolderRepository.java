package org.numamo.category.file.info.service.repository.api;

import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.FolderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends CrudRepository<FolderEntity,Long> {

    List<FolderEntity> findAllByCategoryAndParent (CategoryEntity category,FolderEntity parent);

    Optional<FolderEntity> findByNameAndCategory (String name,CategoryEntity category);

    List<FolderEntity> findAllByParentAndCategory(FolderEntity parent,CategoryEntity category);

}
