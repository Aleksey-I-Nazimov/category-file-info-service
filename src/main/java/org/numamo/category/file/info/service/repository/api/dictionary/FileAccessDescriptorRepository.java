package org.numamo.category.file.info.service.repository.api.dictionary;

import org.numamo.category.file.info.service.repository.entity.dictionary.FileAccessDescriptorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FileAccessDescriptorRepository extends CrudRepository<FileAccessDescriptorEntity, Long> {

    Optional<FileAccessDescriptorEntity> findByCode(String code);

}
