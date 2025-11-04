package com.carguyheaven.carguy_app.Repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.carguyheaven.carguy_app.Models.Evento;

public interface RepoEvento extends JpaRepository<Evento, Integer> {

    public List<Evento> findByNomeContainingIgnoreCaseOrDescrizioneContainingIgnoreCaseOrLuogoContainingIgnoreCase(
            String nome, String descrizione, String luogo,
            Sort sort);
}