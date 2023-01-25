package org.numamo.category.file.info.service.repository.entity;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;
import org.numamo.category.file.info.service.repository.entity.dictionary.FileAccessDescriptorEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="file_access")
public final class FileAccessEntity extends BasicEntity {


    @ManyToOne
    @JoinColumn(name="file_id",nullable=false)
    private FileEntity file;

    @ManyToOne
    @JoinColumn(name="file_access_descriptor_id",nullable=false)
    private FileAccessDescriptorEntity fileAccessDescriptor;

    @ManyToOne
    @JoinColumn(name="user_record_id",nullable=false)
    private UserRecordEntity userRecord;


    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public FileAccessDescriptorEntity getFileAccessDescriptor() {
        return fileAccessDescriptor;
    }

    public void setFileAccessDescriptor(FileAccessDescriptorEntity fileAccessDescriptor) {
        this.fileAccessDescriptor = fileAccessDescriptor;
    }

    public UserRecordEntity getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecordEntity userRecord) {
        this.userRecord = userRecord;
    }


    @Override
    public String toString() {
        return "FileAccessEntity{" +
                "file.id=" + file.getId() +
                ", fileAccessDescriptor=" + fileAccessDescriptor +
                ", userRecord=" + userRecord +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
