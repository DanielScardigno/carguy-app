package com.carguyheaven.carguy_app.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.carguyheaven.carguy_app.Models.Evento;
import com.carguyheaven.carguy_app.Repositories.RepoCategoria;
import com.carguyheaven.carguy_app.Repositories.RepoEvento;
import com.carguyheaven.carguy_app.Repositories.RepoUtente;
import com.carguyheaven.carguy_app.Security.DatabaseUserDetails;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/eventi")
public class ControllerEvento {

    @Autowired
    RepoEvento repoEvento;

    @Autowired
    RepoCategoria repoCategoria;

    @Autowired
    RepoUtente repoUtente;

    @GetMapping
    public String index(Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String luogo,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) Integer categoriaId) {

        // Ordine (nome, data, nuovo)
        Sort sort = Sort.by("id").descending();

        if (order != null) {
            switch (order.toLowerCase()) {
                case "nome":
                    sort = Sort.by("nome").ascending();
                    break;

                case "data":
                    sort = Sort.by("data").ascending();
                    break;

                case "nuovo":
                    sort = Sort.by("id").descending();
                    break;
            }
        } else {
            order = "nuovo";
        }

        // Cerca
        model.addAttribute("eventiEsistenti", repoEvento.findAll());
        
        List<Evento> eventiMostrati = new ArrayList<Evento>();

        if (keyword == null || keyword.isEmpty()) {
            eventiMostrati = repoEvento.findAll(sort);
        } else {
            eventiMostrati = repoEvento
                    .findByNomeContainingIgnoreCaseOrDescrizioneContainingIgnoreCaseOrLuogoContainingIgnoreCase(keyword,
                            keyword, keyword, sort);
        }

        // Filtri (luogo, data, categoria)
        List<Evento> eventiFiltrati = new ArrayList<Evento>();

        for (Evento evento : eventiMostrati) {
            boolean match = true;

            if (luogo != null && !luogo.isEmpty()) {
                if (!evento.getLuogo().equals(luogo)) {
                    match = false;
                }
            }

            if (data != null) {
                if (!evento.getData().equals(data)) {
                    match = false;
                }
            }

            if (categoriaId != null) {
                if (!evento.getCategoria().getId().equals(categoriaId)) {
                    match = false;
                }
            }

            if (match) {
                eventiFiltrati.add(evento);
            }
        }

        model.addAttribute("eventiMostrati", eventiFiltrati);
        model.addAttribute("categorie", repoCategoria.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("order", order);
        model.addAttribute("luogo", luogo);
        model.addAttribute("data", data);
        model.addAttribute("categoriaId", categoriaId);

        List<String> luoghi = new ArrayList<String>();

        for (Evento evento : repoEvento.findAll()) {
            luoghi.add(evento.getLuogo());
        }

        Collections.sort(luoghi);
        model.addAttribute("luoghi", luoghi);

        return "pagine/eventi/index";
    }

    @GetMapping("/crea")
    public String create(Model model) {

        model.addAttribute("evento", new Evento());
        model.addAttribute("categorie", repoCategoria.findAll());

        return "pagine/eventi/create";
    }

    @PostMapping("/crea")
    public String store(
        Model model,
        @Valid
        @ModelAttribute(name = "evento") Evento eventoForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal DatabaseUserDetails databaseUserDetails
    ) {

        model.addAttribute("categorie", repoCategoria.findAll());

        if (bindingResult.hasErrors()) {
            return "pagine/eventi/create";
        }

        eventoForm.setCreatore(repoUtente.findById(databaseUserDetails.getId()).get());
        repoEvento.save(eventoForm);
        return "redirect:/home";
    }

    @GetMapping("/{id}/modifica")
    public String edit(
        Model model,
        @PathVariable Integer id
    ) {

        Optional<Evento> eventoOptional = repoEvento.findById(id);

        if (eventoOptional.isPresent()) {
            model.addAttribute("evento", eventoOptional.get());
            model.addAttribute("categorie", repoCategoria.findAll());
            return "pagine/eventi/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non ci sono eventi con id: " + id);
        }
    }

    @PostMapping("/{id}/modifica")
    public String update(
        Model model,
        @Valid
        @ModelAttribute(name = "evento") Evento eventoForm,
        BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categorie", repoCategoria.findAll());
            return "pagine/eventi/edit";
        } else {
            repoEvento.save(eventoForm);
            return "redirect:/eventi";
        }
    }

    @PostMapping("/{id}/elimina")
    public String delete(
        Model model,
        @PathVariable Integer id
    ) {

        repoEvento.deleteById(id);
        return "redirect:/eventi";
    }
}