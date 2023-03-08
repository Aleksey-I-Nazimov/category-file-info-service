package org.numamo.category.file.info.service.component.api.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.FileObjectDmo;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;

public interface SaveFileComponent {

    void saveCategoryFilesAndFolders(
            final FileObjectDmo fileObjectDmo,
            final CategoryEntity mainCategory,
            final FileSysIndexEntity fileSysIndex
    );

}
