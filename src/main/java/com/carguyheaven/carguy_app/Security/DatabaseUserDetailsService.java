package com.carguyheaven.carguy_app.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carguyheaven.carguy_app.Models.Utente;
import com.carguyheaven.carguy_app.Repositories.RepoUtente;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    RepoUtente repoUtente;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Utente> utenteOptional = repoUtente.findByUsername(username);

        if (utenteOptional.isPresent()) {
            return new DatabaseUserDetails(utenteOptional.get());
        } else {
            throw new UsernameNotFoundException("L'username non esiste");
        }
    }
}
