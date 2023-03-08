package org.numamo.category.file.info.service.controller.dto;

import java.time.Instant;

public final class UserStorageUpdateRqDto {

    private String userLogin;
    private Instant instant;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    @Override
    public String toString() {
        return "UserStorageUpdateRqDto{" +
                "userLogin='" + userLogin + '\'' +
                ", instant=" + instant +
                '}';
    }
}
