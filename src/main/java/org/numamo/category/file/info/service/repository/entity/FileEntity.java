package org.numamo.category.file.info.service.repository.entity;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;
import org.numamo.category.file.info.service.repository.entity.dictionary.FileExtensionEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

/**
 * The entity of the single file info
 *
 * @author Nazimov Aleksey I.
 */
@Entity
@Table(name="file")
public final class FileEntity extends BasicEntity {

    @Column(name="name",nullable=false)
    private String name;

    @Column(name="full_path",nullable=false)
    private String fullPath;

    @Column(name="byte_size")
    private long byteSize;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name="category_id",nullable = false)
    private CategoryEntity category;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name="folder_id",nullable=false)
    private FolderEntity folder;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name="file_extension_id",nullable=false)
    private FileExtensionEntity fileExtension;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name="file_sys_index_id",nullable=false)
    private FileSysIndexEntity fileSysIndex;

    @OneToMany(mappedBy = "file", orphanRemoval = true, cascade = ALL)
    private List<FileAccessEntity> fileAccessList;



    public String getName() {
        return name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Long getByteSize() {
        return byteSize;
    }

    public void setByteSize(Long byteSize) {
        this.byteSize = byteSize;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public FolderEntity getFolder() {
        return folder;
    }

    public void setFolder(FolderEntity folder) {
        this.folder = folder;
    }

    public FileExtensionEntity getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtensionEntity fileExtension) {
        this.fileExtension = fileExtension;
    }

    public FileSysIndexEntity getFileSysIndex() {
        return fileSysIndex;
    }

    public void setFileSysIndex(FileSysIndexEntity fileSysIndex) {
        this.fileSysIndex = fileSysIndex;
    }

    public List<FileAccessEntity> getFileAccessList() {
        return fileAccessList;
    }

    public void setFileAccessList(List<FileAccessEntity> fileAccessList) {
        this.fileAccessList = fileAccessList;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "name='" + name + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", byteSize=" + byteSize +
                ", category=" + category +
                ", folder=" + folder +
                ", fileExtension=" + fileExtension +
                ", fileSysIndex=" + fileSysIndex +
                ", fileAccessList=" + fileAccessList +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
