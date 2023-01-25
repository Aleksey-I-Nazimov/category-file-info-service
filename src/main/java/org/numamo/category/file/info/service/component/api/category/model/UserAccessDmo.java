package org.numamo.category.file.info.service.component.api.category.model;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The DTO object of describing access (set as code) of specified user login
 *
 * @author Nazimov Aleksey I.
 */
public final class UserAccessDmo {

    private String code;
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

    public static Set<String> trimCodes (List<UserAccessDmo> userRecords) {
        final HashSet<String> set = new HashSet<>();
        userRecords.forEach(u->set.add(u.code));
        return set;
    }

    public static Set<String> trimUsers(List<UserAccessDmo> userRecords) {
        final HashSet<String> set = new HashSet<>();
        userRecords.forEach(u->set.add(u.login));
        return set;
    }

    @Override
    public String toString() {
        return "UserAccessDmo{" +
                "code='" + code + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
