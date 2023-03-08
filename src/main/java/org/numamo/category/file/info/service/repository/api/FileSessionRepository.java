package org.numamo.category.file.info.service.repository.api;

import org.numamo.category.file.info.service.repository.entity.FileSessionEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FileSessionRepository extends CrudRepository<FileSessionEntity, Long> {

    Optional<FileSessionEntity> findByIdAndActive(Long id, Boolean active);

    List<FileSessionEntity> findAllByActiveAndFileSysIndex(Boolean active, FileSysIndexEntity fileSysIndex);

    List<FileSessionEntity> findAllByActive(Boolean active);

}
