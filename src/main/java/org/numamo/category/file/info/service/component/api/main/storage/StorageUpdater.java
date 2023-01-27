package org.numamo.category.file.info.service.component.api.main.storage;

/**
 * The interface of putting file info records into the file and folder
 * tables
 *
 * @author Nazimov Aleksey I.
 */
public interface StorageUpdater {


    /**
     * The method of reading and storing the new file records associated
     * with the new index
     *
     * @param fileSysIndexId is the primary key of the file sys index ID
     */
    void update(long fileSysIndexId);

}
