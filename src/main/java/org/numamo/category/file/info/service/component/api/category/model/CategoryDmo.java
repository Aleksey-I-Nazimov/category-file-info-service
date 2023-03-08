package org.numamo.category.file.info.service.component.api.category.model;


import java.util.Objects;

import static java.util.Objects.requireNonNull;


/**
 * The internal model of categories
 *
 * @author Nazimov Aleksey I.
 */
public final class CategoryDmo {

    private final CodeDmo index;
    private final FileObjectDmo fileObject;

    public CategoryDmo(
            CodeDmo index,
            FileObjectDmo fileObject
    ) {
        this.index = requireNonNull(index);
        this.fileObject = requireNonNull(fileObject);
    }

    public CodeDmo getIndex() {
        return index;
    }

    public FileObjectDmo getFileObject() {
        return fileObject;
    }

    @Override
    public String toString() {
        return "CategoryDmo{" +
                "index=" + index +
                ", fileObject=" + fileObject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDmo)) return false;
        CategoryDmo that = (CategoryDmo) o;
        return Objects.equals(getIndex(), that.getIndex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex());
    }
}
