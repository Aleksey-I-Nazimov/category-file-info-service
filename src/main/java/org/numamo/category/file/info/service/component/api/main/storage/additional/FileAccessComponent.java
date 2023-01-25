package org.numamo.category.file.info.service.component.api.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.FileObjectDmo;
import org.numamo.category.file.info.service.repository.entity.FileAccessEntity;
import org.numamo.category.file.info.service.repository.entity.FileEntity;

import java.util.List;

public interface FileAccessComponent {

    List<FileAccessEntity> makeAccessEntity(
            final FileObjectDmo fileObject,
            final FileEntity fileEntity
    );

}
