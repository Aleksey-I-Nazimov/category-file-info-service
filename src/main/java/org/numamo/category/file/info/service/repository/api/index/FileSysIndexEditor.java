package org.numamo.category.file.info.service.repository.api.index;

import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.numamo.category.file.info.service.repository.api.index.exception.ArchivedIndexIllegalStateException;

import java.util.Optional;


/**
 * The interface of the index editor component which allows us
 * 1 finding actual index
 * 2 archiving indexes
 * 3 making index request
 *
 * @author Nazimov Aleksey I.
 */
public interface FileSysIndexEditor {

    /**
     * The method tries to find existed file sys index and creates the new one if there is no one
     * @return the requested file system index record entity
     */
    Optional<FileSysIndexEntity> findRequestedIndex ();

    /**
     * The method tries to find the single actual index
     * @return the actual file system index
     */
    Optional<FileSysIndexEntity> findAppliedActualIndex();

    /**
     * The method checks and creates the first root index.
     * It's used when the database was dropped
     */
    void makeRootActualIndex ();

    /**
     * The method takes the new requested index and makes the following actions:
     * 1. Controls the parent index state and the state of requested index
     *    the parent index state must have ACTUAL state
     *    the new requested index state must have REQUESTED state
     *
     * 2. The requested actual index is transformed to APPLIED state and the ACTUAL index is
     * transformed to ARCHIVED one
     * @param newRequestedSysIndexEntity is the new requested index
     * @throws ArchivedIndexIllegalStateException is throws if the internal control of the index state
     * was failed
     */
    void archiveActualIndex (FileSysIndexEntity newRequestedSysIndexEntity) throws ArchivedIndexIllegalStateException;

}
