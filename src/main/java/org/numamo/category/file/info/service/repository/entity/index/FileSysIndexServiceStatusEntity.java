package org.numamo.category.file.info.service.repository.entity.index;

import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;

import javax.persistence.*;


/**
 * The file system index service status entity is used to make records about enabling and disabling
 * the file index service.
 *
 * ENABLED = TRUE means that the index service can be started automatically or manually.
 * ENABLED = FALSE means that the index service was deactivated manually and indexation is NOT possible
 *
 * ATTENTION: user requests of indexation are going to be rejected if ENABLED==false
 *
 * @author Nazimov Aleksey I.
 *
 */
@Entity
@Table(name="file_sys_index_service_status")
public final class FileSysIndexServiceStatusEntity extends BasicEntity {

    @Column(name="enabled",nullable=false)
    private Boolean enabled = false;

    @Column(name="actual",nullable=false)
    private Boolean actual = false;

    @Column(name="info")
    private String info;

    @OneToOne
    @JoinColumn(name = "prev_file_sys_index_service_status_id")
    private FileSysIndexServiceStatusEntity prevFileSysIndexServiceStatus;


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public FileSysIndexServiceStatusEntity getPrevFileSysIndexServiceStatus() {
        return prevFileSysIndexServiceStatus;
    }

    public void setPrevFileSysIndexServiceStatus(FileSysIndexServiceStatusEntity prevFileSysIndexServiceStatus) {
        this.prevFileSysIndexServiceStatus = prevFileSysIndexServiceStatus;
    }

    @Override
    public String toString() {
        return "FileSysIndexServiceStatusEntity{" +
                "enabled=" + enabled +
                ", actual=" + actual +
                ", info='" + info + '\'' +
                ", prevFileSysIndexServiceStatus=" + prevFileSysIndexServiceStatus +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
