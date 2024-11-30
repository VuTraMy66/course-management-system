package com.example.course_management_system.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

    private Users user;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails() {}

    public CustomUserDetails(Users user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getFullName() {
        return user.getFirstname() + " " + user.getLastname();
    }

    public Users getUser() {
        return user;
    }
    
}
