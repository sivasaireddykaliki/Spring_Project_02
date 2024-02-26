package com.example.springproject2.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    EMPLOYEE,

    ADMIN,

    PRINCIPAL,

    MANAGER;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return simpleGrantedAuthorities;
    }
}
