package org.numamo.category.file.info.service.component.api.main.user.model;

import org.springframework.security.core.GrantedAuthority;


public final class GrantedAuthorityDmo implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityDmo() {
    }

    public GrantedAuthorityDmo(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "GrantedAuthorityDmo{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
