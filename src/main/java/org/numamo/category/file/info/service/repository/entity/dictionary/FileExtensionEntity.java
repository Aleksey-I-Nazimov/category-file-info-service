package org.numamo.category.file.info.service.repository.entity.dictionary;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="file_extension")
public final class FileExtensionEntity extends BasicEntity {

    @Column(name="code",nullable=false,unique=true)
    private String code;

    @Column(name="info",nullable=false)
    private String info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "FileExtensionEntity{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
