package org.numamo.category.file.info.service.repository.api;

import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.FolderEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends CrudRepository<FolderEntity,Long> {

    List<FolderEntity> findAllByCategoryAndParentAndFileSysIndex(CategoryEntity category, FolderEntity parent, FileSysIndexEntity fileSysIndexEntity);

    Optional<FolderEntity> findByIdAndFileSysIndex(Long id, FileSysIndexEntity fileSysIndex);

    List<FolderEntity> findAllByParentAndFileSysIndex(FolderEntity parent, FileSysIndexEntity fileSysIndex);

}
