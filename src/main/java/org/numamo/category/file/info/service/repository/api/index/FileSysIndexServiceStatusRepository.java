package org.numamo.category.file.info.service.repository.api.index;

import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexServiceStatusEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static javax.persistence.LockModeType.PESSIMISTIC_READ;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;


@Repository
public interface FileSysIndexServiceStatusRepository extends CrudRepository<FileSysIndexServiceStatusEntity, Long> {

    @Lock(PESSIMISTIC_READ)
    List<FileSysIndexServiceStatusEntity> findAllByActual(Boolean actual);

    @Lock(PESSIMISTIC_WRITE)
    @SuppressWarnings("unchecked")
    FileSysIndexServiceStatusEntity save(FileSysIndexServiceStatusEntity entity);

}
