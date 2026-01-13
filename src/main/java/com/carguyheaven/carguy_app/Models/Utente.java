package com.carguyheaven.carguy_app.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "utenti")
public class Utente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci l''username")
    private String username;

    @NotBlank(message = "Inserisci la password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_ruolo", nullable = true)
    @JsonBackReference
    private Ruolo ruolo;

    @OneToMany(mappedBy = "creatore")
    private List<Evento> eventi;

    // Getter e Setter

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return this.ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public List<Evento> getEventi() {
        return this.eventi;
    }

    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }
}
