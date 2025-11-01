package com.carguyheaven.carguy_app.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.carguyheaven.carguy_app.Models.Categoria;
import com.carguyheaven.carguy_app.Repositories.RepoCategoria;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categorie")
public class ControllerCategoria {
    
    @Autowired
    RepoCategoria repoCategoria;

    @GetMapping
    public String index(Model model, @RequestParam(required = false) String keyword) {

        model.addAttribute("categorieEsistenti", repoCategoria.findAll());

        List<Categoria> categorieMostrate = new ArrayList<Categoria>();

        if (keyword == null || keyword.isEmpty()) {
            categorieMostrate = repoCategoria.findAll();
        } else {
            categorieMostrate = repoCategoria.findByNomeContainingIgnoreCaseOrDescrizioneContainingIgnoreCase(keyword, keyword);
        }

        model.addAttribute("categorieMostrate", categorieMostrate);
        model.addAttribute("keyword", keyword);

        return "pagine/categorie/index";
    }

    @GetMapping("/crea")
    public String create(Model model) {

        model.addAttribute("categoria", new Categoria());

        return "pagine/categorie/create";
    }

    @PostMapping("/crea")
    public String store(Model model, @Valid @ModelAttribute(name = "categoria") Categoria categoriaForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "pagine/categorie/create";
        }

        repoCategoria.save(categoriaForm);
        return "redirect:/categorie";
    }

    @GetMapping("/{id}/modifica")
    public String edit(Model model, @PathVariable Integer id) {

        Optional<Categoria> categoriaOptional = repoCategoria.findById(id);

        if (categoriaOptional.isPresent()) {
            model.addAttribute("categoria", categoriaOptional.get());
            return "pagine/categorie/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non ci sono categorie con id: " + id);
        }
    }
    
    @PostMapping("/{id}/modifica")
    public String update(Model model, @Valid @ModelAttribute(name = "categoria") Categoria categoriaForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "pagine/categorie/edit";
        } else {
            repoCategoria.save(categoriaForm);
            return "redirect:/categorie";
        }
    }

    @PostMapping("/{id}/elimina")
    public String delete(Model model, @PathVariable Integer id) {

        repoCategoria.deleteById(id);
        return "redirect:/categorie";
    }
}
