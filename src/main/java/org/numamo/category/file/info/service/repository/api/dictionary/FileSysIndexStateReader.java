package org.numamo.category.file.info.service.repository.api.dictionary;

import org.numamo.category.file.info.service.repository.entity.dictionary.FileSysIndexStateEntity;

public interface FileSysIndexStateReader {

    FileSysIndexStateEntity findRequestedState();

    FileSysIndexStateEntity findAppliedState();

    FileSysIndexStateEntity findArchivedState();

}
