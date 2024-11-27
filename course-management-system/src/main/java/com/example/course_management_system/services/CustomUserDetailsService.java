package com.example.course_management_system.services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.CustomUserDetails;
import com.example.course_management_system.models.Users;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userService.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String role = user.getRole().toUpperCase();
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(role));
        System.out.println("Loaded User: " + user.getUsername() + " with Role: " + role);

        return new CustomUserDetails(user, grantedAuthoritySet);
    }
}
