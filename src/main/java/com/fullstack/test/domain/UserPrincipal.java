package com.fullstack.test.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new UserAuthority(this.user));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // just a test stub
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // just a test stub
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // just a test stub
    }

    @Override
    public boolean isEnabled() {
        return true; // just a test stub
    }

    public User getUser() {
        return user;
    }
}
