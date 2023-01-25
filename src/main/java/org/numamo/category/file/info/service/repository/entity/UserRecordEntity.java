package org.numamo.category.file.info.service.repository.entity;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_record")
public final class UserRecordEntity extends BasicEntity {

    @Column(name="code",nullable=false,unique=true)
    private String code;

    @Column(name="login",nullable=false,unique=true)
    private String login;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "UserRecordEntity{" +
                "code='" + code + '\'' +
                ", login='" + login + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
