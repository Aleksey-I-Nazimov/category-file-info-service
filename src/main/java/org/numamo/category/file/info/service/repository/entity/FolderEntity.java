package org.numamo.category.file.info.service.repository.entity;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;

import javax.persistence.*;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;


@Entity
@Table(name = "folder")
public final class FolderEntity extends BasicEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "parent_id")
    private FolderEntity parent;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "file_sys_index_id", nullable = false)
    private FileSysIndexEntity fileSysIndex;


    public String getName() {
        return name;
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

    public FolderEntity getParent() {
        return parent;
    }

    public void setParent(FolderEntity parent) {
        this.parent = parent;
    }

    public FileSysIndexEntity getFileSysIndex() {
        return fileSysIndex;
    }

    public void setFileSysIndex(FileSysIndexEntity fileSysIndex) {
        this.fileSysIndex = fileSysIndex;
    }

    @Override
    public String toString() {
        return "FolderEntity{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", parent=" + parent +
                ", fileSysIndex=" + fileSysIndex +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
