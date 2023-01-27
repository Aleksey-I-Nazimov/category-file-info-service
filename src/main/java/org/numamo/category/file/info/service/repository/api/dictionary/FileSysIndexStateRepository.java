package org.numamo.category.file.info.service.repository.api.dictionary;

import org.numamo.category.file.info.service.repository.entity.dictionary.FileSysIndexStateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileSysIndexStateRepository extends CrudRepository<FileSysIndexStateEntity, Long> {

    Optional<FileSysIndexStateEntity> findByCode(String code);

}
