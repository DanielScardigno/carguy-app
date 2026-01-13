package com.carguyheaven.carguy_app.Models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "eventi")
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci il nome dell''''evento")
    private String nome;

    @Column(length = 5000)
    @NotBlank(message = "Inserisci la descrizione dell''evento")
    private String descrizione;

    @NotBlank(message = "Inserisci il luogo dell''evento")
    private String luogo;

    @NotNull(message = "Inserisci la data dell''evento")
    @FutureOrPresent(message = "La data inserita non è valida")
    private LocalDate data;
    
    @NotNull(message = "Inserisci l''orario di inizio dell''evento")
    private LocalTime orarioDiInizio;

    @NotNull(message = "inserisci l''orario di fine dell''evento")
    private LocalTime orarioDiFine;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    @NotNull(message = "Seleziona una categoria")
    @JsonBackReference
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_creatore", nullable = false)
    @JsonBackReference
    private Utente creatore;

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

    public String getLuogo() {
        return this.luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOrarioDiInizio() {
        return this.orarioDiInizio;
    }

    public void setOrarioDiInizio(LocalTime orarioDiInizio) {
        this.orarioDiInizio = orarioDiInizio;
    }

    public LocalTime getOrarioDiFine() {
        return this.orarioDiFine;
    }

    public void setOrarioDiFine(LocalTime orarioDiFine) {
        this.orarioDiFine = orarioDiFine;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Utente getCreatore() {
        return this.creatore;
    }

    public void setCreatore(Utente creatore) {
        this.creatore = creatore;
    }
}