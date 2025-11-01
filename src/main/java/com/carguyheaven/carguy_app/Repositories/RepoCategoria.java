package com.carguyheaven.carguy_app.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carguyheaven.carguy_app.Models.Categoria;

public interface RepoCategoria extends JpaRepository<Categoria, Integer> {
    
    public List<Categoria> findByNomeContainingIgnoreCaseOrDescrizioneContainingIgnoreCase(String nome, String descrizione);
}
