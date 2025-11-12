package com.carguyheaven.carguy_app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carguyheaven.carguy_app.Models.Ruolo;

public interface RepoRuolo extends JpaRepository<Ruolo, Integer> {
    
    public Ruolo findByNome(String nome);
}
