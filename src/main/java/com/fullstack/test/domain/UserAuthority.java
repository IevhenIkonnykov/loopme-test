package com.fullstack.test.domain;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {
    private User user;

    public UserAuthority(User user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return this.user.getRole().name();
    }
}
