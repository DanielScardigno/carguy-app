package com.carguyheaven.carguy_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carguyheaven.carguy_app.Models.Utente;
import com.carguyheaven.carguy_app.Repositories.RepoRuolo;
import com.carguyheaven.carguy_app.Repositories.RepoUtente;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/registrati")
public class ControllerUtente {
    
    @Autowired
    RepoUtente repoUtente;

    @Autowired
    RepoRuolo repoRuolo;

    @GetMapping
    public String create(Model model) {

        model.addAttribute("Utente", new Utente());

        return "pagine/registrati";
    }

    @PostMapping
    public String store(
        Model model,
        @Valid
        @ModelAttribute(name = "Utente") Utente formUtente,
        BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "pagine/registrati";
        } else {
            formUtente.setRuolo(repoRuolo.findByNome("UTENTE"));

            String formPassword = formUtente.getPassword();

            if (!formPassword.startsWith("{noop}")) {
                formUtente.setPassword("{noop}" + formPassword);
            }
            
            repoUtente.save(formUtente);
            return "redirect:/login";
        }
    }
}