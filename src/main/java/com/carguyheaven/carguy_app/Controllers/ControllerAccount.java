package com.carguyheaven.carguy_app.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carguyheaven.carguy_app.Models.Evento;
import com.carguyheaven.carguy_app.Models.Utente;
import com.carguyheaven.carguy_app.Repositories.RepoEvento;
import com.carguyheaven.carguy_app.Repositories.RepoUtente;
import com.carguyheaven.carguy_app.Security.DatabaseUserDetails;

@Controller
@RequestMapping("/account")
public class ControllerAccount {
    
    @Autowired
    RepoEvento repoEvento;

    @Autowired
    RepoUtente repoUtente;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal DatabaseUserDetails databaseUserDetails) {

        Utente utente = repoUtente.findById(databaseUserDetails.getId()).get();
        model.addAttribute("utente", utente);

        List<Evento> eventi = new ArrayList<Evento>();

        for (Evento evento : repoEvento.findAll()) {
            if (evento.getCreatore().getId().equals(utente.getId())) {
                eventi.add(evento);
            }
        }

        model.addAttribute("eventi", eventi);
        return "pagine/account";
    }
}
