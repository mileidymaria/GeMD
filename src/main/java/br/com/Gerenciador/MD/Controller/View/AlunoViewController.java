package br.com.Gerenciador.MD.Controller.View;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Services.*;

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

@Controller
@RequestMapping(path = "/alunos")
public class AlunoViewController {

    @Autowired
    private AlunoService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("alunos", service.findAll());
        return "alunos";
    }

    @GetMapping(path = "/aluno")
    public String cadastro(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("modalidade", TipoModalidadeEnum.values());
        return "formAluno";
    }

    @PostMapping(path = "/aluno")
    public String salvar(@Valid @ModelAttribute Aluno aluno, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formAluno";
        }
        aluno.setId(null);
        try {
            service.save(aluno);
            model.addAttribute("msgSucesso", "Aluno cadastrado com sucesso.");
            model.addAttribute("aluno", new Aluno());
            return "formAluno";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("aluno", e.getMessage()));
            return "formAluno";
        }
    }
    
    @GetMapping(path = "/aluno/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("aluno", service.findById(id));
        return "formAluno";
    }
    
    @PostMapping(path = "/aluno/{id}")
    public String atualizar(@Valid @ModelAttribute Aluno aluno, BindingResult result, @PathVariable("id") Long id, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formAluno";
        }
        aluno.setId(id);
        try {
            service.update(aluno);
            model.addAttribute("msgSucesso", "Aluno cadastrado com sucesso!");
            model.addAttribute("aluno", aluno);
            return "formAluno";
        
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("aluno", e.getMessage()));
            return "formAluno";
        }
    }
    
    @GetMapping(path = "{id}/deletar")
    public String deletar(@PathVariable("id") Long id){
        service.delete(id);
        return "redirect:/alunos";
    }
}