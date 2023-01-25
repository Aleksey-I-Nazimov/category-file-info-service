package org.numamo.category.file.info.service.repository.api;

import org.numamo.category.file.info.service.repository.entity.FileAccessEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAccessRepository extends CrudRepository<FileAccessEntity,Long> {
}
