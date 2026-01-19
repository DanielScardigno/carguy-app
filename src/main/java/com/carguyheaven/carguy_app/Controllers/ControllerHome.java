package com.carguyheaven.carguy_app.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carguyheaven.carguy_app.Models.Evento;
import com.carguyheaven.carguy_app.Repositories.RepoCategoria;
import com.carguyheaven.carguy_app.Repositories.RepoEvento;

@Controller
public class ControllerHome {

    @Autowired
    RepoEvento repoEvento;

    @Autowired
    RepoCategoria repoCategoria;

    @GetMapping("/")
    public String redirectHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String luogo,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) Integer categoriaId) {

        // Ordine
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

        // Filtri
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
            if (!luoghi.contains(evento.getLuogo())) {
                luoghi.add(evento.getLuogo());
            }
        }

        Collections.sort(luoghi);
        model.addAttribute("luoghi", luoghi);

        return "pagine/home";
    }
}