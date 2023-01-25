package org.numamo.category.file.info.service.repository.entity;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;

import javax.persistence.*;


/**
 * The session of user file access
 * ACTIVE = TRUE means the current session is active and user works with files
 * ACTIVE = FALSE means the expired session
 * All sessions are bound to the respective file system index
 * File indexation service controls active sessions and do not work if any active exist
 *
 * @author Nazimov Aleksey I.
 */
@Entity
@Table(name="file_session")
public final class FileSessionEntity extends BasicEntity {

    @Column(name="active",nullable=false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name="user_record_id",nullable=false)
    private UserRecordEntity userRecord;

    @ManyToOne
    @JoinColumn(name="file_sys_index_id",nullable=false)
    private FileSysIndexEntity fileSysIndex;

    @Column(name="info")
    private String info;


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UserRecordEntity getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecordEntity userRecord) {
        this.userRecord = userRecord;
    }

    public FileSysIndexEntity getFileSysIndex() {
        return fileSysIndex;
    }

    public void setFileSysIndex(FileSysIndexEntity fileSysIndex) {
        this.fileSysIndex = fileSysIndex;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "FileSessionEntity{" +
                "active=" + active +
                ", userRecord=" + userRecord +
                ", fileSysIndex=" + fileSysIndex +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
