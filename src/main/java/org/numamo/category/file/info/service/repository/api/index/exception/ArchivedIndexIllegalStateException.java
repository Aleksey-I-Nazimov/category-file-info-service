package org.numamo.category.file.info.service.repository.api.index.exception;

public class ArchivedIndexIllegalStateException extends RuntimeException {

    public ArchivedIndexIllegalStateException(Object loggedObject) {
        super("The requested of archiving index has wrong params: " + loggedObject);
    }

}
