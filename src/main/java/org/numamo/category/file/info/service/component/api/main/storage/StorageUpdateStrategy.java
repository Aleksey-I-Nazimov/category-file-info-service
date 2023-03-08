package org.numamo.category.file.info.service.component.api.main.storage;

import org.numamo.category.file.info.service.controller.dto.FileStorageUpdaterDto;


/**
 * The interface of the main file info storage updater
 *
 * @author Nazimov Aleksey I.
 */
public interface StorageUpdateStrategy {

    /**
     * The method of executing file processor strategy
     * It returns the processor status enum
     *
     * @return the reference of processor status
     */
    FileStorageUpdaterDto execute();


}
