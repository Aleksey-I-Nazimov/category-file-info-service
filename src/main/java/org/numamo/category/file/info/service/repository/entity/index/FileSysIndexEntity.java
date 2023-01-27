package org.numamo.category.file.info.service.repository.entity.index;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;
import org.numamo.category.file.info.service.repository.entity.dictionary.FileSysIndexStateEntity;

import javax.persistence.*;


/**
 * The current file system index record.
 * The file system index record describes the process of reading file sources.
 * Reading has a state described by the fileSysIndexState property
 *
 * @author Nazimov Aleksey I.
 */
@Entity
@Table(name="file_sys_index")
public final class FileSysIndexEntity extends BasicEntity {

    @Column(name="info")
    private String info;

    @Column(name="number",nullable = false,unique=true)
    private long number;

    @ManyToOne
    @JoinColumn(name = "file_sys_index_state_id", nullable = false)
    private FileSysIndexStateEntity fileSysIndexState;

    @OneToOne
    @JoinColumn(name="prev_file_sys_index_id")
    private FileSysIndexEntity prevFileSysIndex;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public FileSysIndexStateEntity getFileSysIndexState() {
        return fileSysIndexState;
    }

    public void setFileSysIndexState(FileSysIndexStateEntity fileSysIndexState) {
        this.fileSysIndexState = fileSysIndexState;
    }

    public FileSysIndexEntity getPrevFileSysIndex() {
        return prevFileSysIndex;
    }

    public void setPrevFileSysIndex(FileSysIndexEntity prevFileSysIndex) {
        this.prevFileSysIndex = prevFileSysIndex;
    }

    @Override
    public String toString() {
        return "FileSysIndexEntity{" +
                "info='" + info + '\'' +
                ", number=" + number +
                ", fileSysIndexState=" + fileSysIndexState +
                ", prevFileSysIndex=" + prevFileSysIndex +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
