package org.numamo.category.file.info.service.repository.entity.user;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_record")
public final class UserRecordEntity extends BasicEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @OneToOne(mappedBy = "userRecord")
    private UserInfoEntity userInfo;

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

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "UserRecordEntity{" +
                "code='" + code + '\'' +
                ", login='" + login + '\'' +
                ", userInfo=" + userInfo +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
