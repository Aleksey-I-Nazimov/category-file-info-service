package org.numamo.category.file.info.service.component.api.main.session;

import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexServiceStatusEntity;


public interface FileSysIndexServiceStatusComponent {

    void changeStatus(boolean enabled);

    FileSysIndexServiceStatusEntity readActualStatus();

}
