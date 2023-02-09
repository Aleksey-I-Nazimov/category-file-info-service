package org.numamo.category.file.info.service.repository.api.dictionary;

import org.numamo.category.file.info.service.repository.entity.dictionary.FileExtensionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileExtensionRepository extends CrudRepository<FileExtensionEntity, Long> {

    Optional<FileExtensionEntity> findByCode(String code);

}
