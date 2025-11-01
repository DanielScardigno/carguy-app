package com.carguyheaven.carguy_app.Models;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categorie")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci il nome della categoria")
    private String nome;

    @Column(length = 5000)
    @NotBlank(message = "Inserisci la descrizione della categoria")
    private String descrizione;

    @OneToMany(mappedBy = "categoria")
    private List<Evento> eventi;

    // Getter e Setter

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Evento> getEventi() {
        return this.eventi;
    }

    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }
}