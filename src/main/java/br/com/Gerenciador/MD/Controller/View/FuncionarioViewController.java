package br.com.Gerenciador.MD.Controller.View;

import br.com.Gerenciador.MD.Exception.NotFoundException;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Repository.PermissaoRepository;
import br.com.Gerenciador.MD.Services.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

@Controller
@RequestMapping(path = "/funcionarios")
public class FuncionarioViewController {

    @Autowired
    private FuncionarioService service;
    @Autowired
    private PermissaoRepository permissaoRepo;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("funcionarios", service.findAll());
        return "funcionarios";
    }

    @GetMapping(path = "/funcionario")
    public String cadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formFuncionario";
    }

    @PostMapping(path = "/funcionario")
    public String save(@Valid @ModelAttribute Funcionario funcionario,
            BindingResult result,
            @RequestParam("confirmarSenha") String confirmarSenha,Model model) {

         model.addAttribute("permissoes", permissaoRepo.findAll());
         
        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formFuncionario";
        }

        if (!funcionario.getSenha().equals(confirmarSenha)) {
            model.addAttribute("msgErros", new ObjectError("funcionario", "Senhas não conferem."));
            return "formFuncionario";
        }

        funcionario.setId((Long)null);
        try {
            service.save(funcionario);
            model.addAttribute("msgSucesso", "Funcionário cadastrado com sucesso!");
            model.addAttribute("funcionario", new Funcionario());
            return "formFuncionario";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionario", e.getMessage()));
            return "formFuncionario";
        }
    }

    @GetMapping(path = "/funcionario/{id}")
    public String atualizacao(@PathVariable("id") Long id, Model model) throws NotFoundException{
        model.addAttribute("funcionario", service.findById(id));
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formFuncionario";
    }

    @PostMapping(path = "/funcionario/{id}")
    public String update(@Valid @ModelAttribute Funcionario funcionario,
            BindingResult result,
            @PathVariable("id") Long id,
            @RequestParam("confirmarSenha") String confirmarSenha,
            Model model) {
        
        model.addAttribute("permissoes", permissaoRepo.findAll());  
        
        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("senha")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formFuncionario";
        }      
        
        if (!funcionario.getSenha().equals(confirmarSenha)) {
            model.addAttribute("msgErros", new ObjectError("funcionario", "Senhas não conferem."));
            return "formFuncionario";
        }        
        
        funcionario.setId(id);
        try {
            service.update(funcionario, "", "", "");
            model.addAttribute("msgSucesso", "Funcionário atualizado!");
            model.addAttribute("funcionario", funcionario);
            return "formFuncionario";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionario", e.getMessage()));
            return "formFuncionario";
        }
    }

    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) throws NotFoundException {
        service.delete(id);
        return "redirect:/funcionarios";
    }

//------------ MEUS DADOS  ----------------
    @GetMapping(path = "/meusdados")
    public String getMeusDados(@AuthenticationPrincipal User user, Model model){
        Funcionario funcionario = service.findByEmail(user.getUsername());
        model.addAttribute("funcionario", funcionario);
        return "formMeusDados";
    }
    
    @PostMapping(path = "/meusdados")
    public String updateMeusDados(
            @Valid @ModelAttribute Funcionario funcionario,
            BindingResult result,
            @AuthenticationPrincipal User user,
            @RequestParam("senhaAtual") String senhaAtual,
            @RequestParam("novaSenha") String novaSenha,
            @RequestParam("confirmarNovaSenha") String confirmarNovaSenha,
            Model model) {
        
        List<FieldError> list = new ArrayList<>();
        for(FieldError fe : result.getFieldErrors()){
            if(!fe.getField().equals("senha") && !fe.getField().equals("permissoes") ){
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formMeusDados";
        }

        Funcionario funcionarioBD = service.findByEmail(user.getUsername());
        if(!funcionarioBD.getId().equals(funcionario.getId())){
            throw new RuntimeException("Acesso negado.");
        }
        try {
            funcionario.setPermissoes(funcionarioBD.getPermissoes());
            service.update(funcionario, senhaAtual, novaSenha, confirmarNovaSenha);
            model.addAttribute("msgSucesso", "Funcionário atualizado com sucesso.");
            model.addAttribute("funcionario", funcionario);
            return "formMeusDados";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionario", e.getMessage()));
            return "formMeusDados";
        }
    }

}    
   
