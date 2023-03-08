package org.numamo.category.file.info.service.controller.dto;


public final class UserSessionRqDto {

    private String userLogin;
    private String existedSessionId;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getExistedSessionId() {
        return existedSessionId;
    }

    public void setExistedSessionId(String existedSessionId) {
        this.existedSessionId = existedSessionId;
    }

    @Override
    public String toString() {
        return "UserSessionRqDto{" +
                "userLogin='" + userLogin + '\'' +
                ", existedSessionId=" + existedSessionId +
                '}';
    }

}
