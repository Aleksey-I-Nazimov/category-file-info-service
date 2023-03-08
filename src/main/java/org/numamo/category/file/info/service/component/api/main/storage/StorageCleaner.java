package org.numamo.category.file.info.service.component.api.main.storage;


/**
 * The interface of storage cleaner. The storage cleaner removes all
 * file records from the file and folder tables
 *
 * @author Nazimov Aleksey I.
 */
public interface StorageCleaner {

    void cleanPrevRecords();

}
