package com.carguyheaven.carguy_app.Security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.carguyheaven.carguy_app.Models.Utente;

public class DatabaseUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(Utente utente) {

        this.id = utente.getId();
        this.username = utente.getUsername();
        this.password = utente.getPassword();

        this.authorities = new HashSet<GrantedAuthority>();

        this.authorities.add(new SimpleGrantedAuthority(utente.getRuolo().getNome()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Integer getId() {
        return this.id;
    }
}