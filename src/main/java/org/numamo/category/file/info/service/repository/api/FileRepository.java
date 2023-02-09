package org.numamo.category.file.info.service.repository.api;

import org.numamo.category.file.info.service.repository.entity.FileEntity;
import org.numamo.category.file.info.service.repository.entity.FolderEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<FileEntity,Long> {

    List<FileEntity> findAllByFolderAndFileSysIndex(FolderEntity folder, FileSysIndexEntity fileSysIndex);

}
