package org.numamo.category.file.info.service.component.api.main.session;


import java.util.Optional;


/**
 * The interface of file system index component.
 * This component requests file system index and returns ID.
 * The index must be finalized. Without finalization the new requests are forbidden.
 *
 * @author Nazimov Aleksey I.
 */
public interface FileSysIndexComponent {

    /**
     * The method creates the new index and returns their ID.
     * The method doesn't create the new index
     *
     * @return
     */
    Optional<Long> getRequestedFileSysIndex();

    /**
     * The method finalizes the process of requested index.
     * The requiested index becomes applied
     *
     * @param fileSysIndexId is the primary index key
     */
    void finalizeRequestedIndex(long fileSysIndexId);

    /**
     * The method removes the file sys index
     *
     * @param fileSysIndexId is the primary index key
     */
    void removeRequestedIndex(long fileSysIndexId);

}
