package br.com.Gerenciador.MD.Controller.View;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Services.*;

import javassist.NotFoundException;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/materiais")
public class MaterialViewController {
    
    @Autowired
    private MaterialService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("materiais", service.findAll());
        return "materiais";
    }
    
    @GetMapping(path = "/material")
    public String cadastro(Model model) {
        model.addAttribute("material", new Material());    
        model.addAttribute("tiposModalidade", TipoModalidadeEnum.values());
        return "formMaterial";
    }

   @PostMapping(path = "/material")
    public String save(@Valid @ModelAttribute Material material, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        
        model.addAttribute("tiposModalidade", TipoModalidadeEnum.values());
        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formMaterial";
        }
        material.setId(null);
        try {
            service.save(material, file);
            model.addAttribute("msgSucesso", "Material cadastrado com sucesso.");
            model.addAttribute("material", new Material());
            return "formMaterial";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Material", e.getMessage()));
            return "formMaterial";
        }
    }
    
      @GetMapping(path = "/material/{id}")
    public String alterar(@PathVariable("id") Long id,Model model) throws NotFoundException {
        model.addAttribute("material", service.findById(id));
        model.addAttribute("tiposModalidade", TipoModalidadeEnum.values());
        return "formMaterial";
    }
    
    @PostMapping(path = "/material/{id}")
    public String update(@Valid @ModelAttribute Material material, BindingResult result, @PathVariable("id") Long id, @RequestParam("file") MultipartFile file, Model model) {

        model.addAttribute("tiposModalidade", TipoModalidadeEnum.values());
        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formMaterial";
        }
        material.setId(id);
        try {
            service.update(material, file);
            model.addAttribute("msgSucesso", "Material atualizado!");
            model.addAttribute("material", material);
            return "formMaterial";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Material", e.getMessage()));
            return "formMaterial";
        }
    }
        
     @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) throws NotFoundException {
        service.delete(id);
        return "redirect:/materiais";
    }

}
