package org.numamo.category.file.info.service.repository.entity.dictionary;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

import static org.numamo.category.file.info.service.repository.impl.dictionary.FileSysIndexStateReaderImpl.*;


@Entity
@Table(name = "file_sys_index_state")
public final class FileSysIndexStateEntity extends BasicEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "info", nullable = false)
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

    public boolean isArchived() {
        return Objects.equals(getCode(), ARCHIVED);
    }

    public boolean isRequested() {
        return Objects.equals(getCode(), REQUESTED);
    }

    public boolean isApplied() {
        return Objects.equals(getCode(), APPLIED);
    }

    @Override
    public String toString() {
        return "FileSysIndexStateEntity{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
