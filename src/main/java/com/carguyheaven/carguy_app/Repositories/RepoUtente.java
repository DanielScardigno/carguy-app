package com.carguyheaven.carguy_app.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carguyheaven.carguy_app.Models.Utente;

public interface RepoUtente extends JpaRepository<Utente, Integer> {
    
    public Optional<Utente> findByUsername(String username);
}
