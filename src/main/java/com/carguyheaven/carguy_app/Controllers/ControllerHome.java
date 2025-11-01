package com.carguyheaven.carguy_app.Controllers;

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

    @Autowired RepoEvento repoEvento;

    @Autowired RepoCategoria repoCategoria;
    
    @GetMapping("/")
    public String redirectHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) String order) {

        model.addAttribute("eventiEsistenti", repoEvento.findAll());

        Sort sort = Sort.unsorted();

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
        }

        List<Evento> eventiMostrati = new ArrayList<Evento>();

        if (keyword == null || keyword.isEmpty()) {
            eventiMostrati = repoEvento.findAll(sort);
        } else {
            eventiMostrati = repoEvento.findByNomeContainingIgnoreCaseOrDescrizioneContainingIgnoreCase(keyword, keyword, sort);
        }

        model.addAttribute("eventiMostrati", eventiMostrati);
        model.addAttribute("categorie", repoCategoria.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("order", order);
    
        List<String> luoghi = new ArrayList<String>();
        for (Evento evento : repoEvento.findAll()) {
            luoghi.add(evento.getLuogo());
        }

        Collections.sort(luoghi);
        model.addAttribute("luoghi", luoghi);

        return "pagine/home";
    }
}
