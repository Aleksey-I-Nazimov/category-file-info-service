package org.numamo.category.file.info.service.component.api.category.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.reverse;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.nonNull;


/**
 * The main file object used within a category model
 *
 * @author Nazimov Aleksey I.
 */
public final class FileObjectDmo {


    // Variables and constructors:-------------------------------------------------------
    public static final String DEFAULT_FILE_SEPARATOR = "/";
    public static final String EXTENSION_SEPARATOR_REGEXP = "\\.";

    private final String name;
    private final List<UserAccessDmo> userAccessList=new ArrayList<> ();

    private final FileObjectDmo parent;
    private final List<FileObjectDmo> childList=new ArrayList<>();
    private final long byteSize;

    public FileObjectDmo(
            String name,
            FileObjectDmo parent
    ) {
        this.name = name;
        this.parent = parent;
        this.byteSize = -1;
    }

    public FileObjectDmo(
            String name,
            FileObjectDmo parent,
            long byteSize
    ) {
        this.name = name;
        this.parent = parent;
        this.byteSize = byteSize;
    }


    // Public API:-----------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public List<UserAccessDmo> getUserAccessList() {
        return userAccessList;
    }

    public FileObjectDmo getParent() {
        return parent;
    }

    public List<FileObjectDmo> getChildList() {
        return childList;
    }

    public boolean isFile () {
        return childList.isEmpty() && byteSize>=0;
    }

    public String getExtension () {
        if (isFile()) {
            try {
                return this.name.split(EXTENSION_SEPARATOR_REGEXP)[1];
            } catch (Exception e) {
                throw new IllegalStateException("Extracting file extension was failed: cause->",e);
            }
        } else {
            throw new IllegalStateException("This file object has no extensions");
        }
    }

    public List<String> getAllExtensions () {
        final List<String> extensions = new ArrayList<>();
        if (isFile()) {
            extensions.add(getExtension());
        } else {
            getChildList().forEach(child->extensions.addAll(child.getAllExtensions()));
        }
        return extensions;
    }

    public List<UserAccessDmo> getAllUsers () {
        final List<UserAccessDmo> accessList = new ArrayList<>(getUserAccessList());
        getChildList().forEach(child->accessList.addAll(child.getAllUsers()));
        return unmodifiableList(accessList);
    }

    public List<String> getFullPathElements () {
        final List<String> pathElements = new ArrayList<> ();
        pathElements.add(getName());
        for (FileObjectDmo parent = this.getParent(); nonNull(parent); parent = parent.getParent()) {
            pathElements.add(parent.getName());
        }
        reverse(pathElements);
        return unmodifiableList(pathElements);
    }

    public String getFullPath () {
        final List<String> pathElements = getFullPathElements();
        final StringBuilder pathBuilder = new StringBuilder(DEFAULT_FILE_SEPARATOR);
        final Iterator<String> pathElementsIterator = pathElements.iterator();
        while(true) {
            pathBuilder.append(pathElementsIterator.next());
            if (pathElementsIterator.hasNext()) {
                pathBuilder.append(DEFAULT_FILE_SEPARATOR);
            } else {
                break;
            }
        }
        return pathBuilder.toString();
    }

    public long getByteSize () {
        long cnt[]={0L};
        if (isFile()) {
            cnt[0]=byteSize;
        } else {
            getChildList().forEach(child-> cnt[0]+=child.getByteSize());
        }
        return cnt[0];
    }


    @Override
    public String toString() {
        // Parent must be excluded from toString:
        return "FileObjectDmo{" +
                "name='" + name + '\'' +
                ", userAccessList=" + userAccessList +
                ", childList=" + childList +
                ", fullPath=" + getFullPath() +
                ", byteSize=" + getByteSize() +
                '}';
    }
}
