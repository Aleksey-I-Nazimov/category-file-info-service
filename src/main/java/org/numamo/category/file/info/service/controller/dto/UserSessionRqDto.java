package org.numamo.category.file.info.service.controller.dto;


public final class UserSessionRqDto {

    private String userLogin;
    private Long existedSessionId;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getExistedSessionId() {
        return existedSessionId;
    }

    public void setExistedSessionId(Long existedSessionId) {
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
