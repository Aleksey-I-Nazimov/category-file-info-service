package org.numamo.category.file.info.service.repository.api.index;

import org.numamo.category.file.info.service.repository.entity.dictionary.FileSysIndexStateEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileSysIndexRepository extends CrudRepository<FileSysIndexEntity,Long> {

    List<FileSysIndexEntity> findAllByFileSysIndexState(FileSysIndexStateEntity fileSysIndexState);

}
