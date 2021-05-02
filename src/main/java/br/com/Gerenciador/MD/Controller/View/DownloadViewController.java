package br.com.Gerenciador.MD.Controller.View;

import br.com.Gerenciador.MD.Exception.NotFoundException;

import br.com.Gerenciador.MD.Gerenciamento.*;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/downloads")
public class DownloadViewController {
    
     @Autowired
    private DownloadService bmservice;
    @Autowired
    private MaterialService mservice;
    @Autowired
    private FuncionarioService fservice;
    @Autowired
    private AlunoService aservice;

            @GetMapping
    public String getAll(Model model) {
        model.addAttribute("downloads", bmservice.findAll()); 
        return "downloads";
    }
    
    @GetMapping(path = "/download")
    public String cadastro(Model model) {
        model.addAttribute("download", new Download()); 
        model.addAttribute("materiais", mservice.findAll());
        model.addAttribute("funcionarios", fservice.findAll());
        model.addAttribute("alunos", aservice.findAll());        
        return "formDownload";
    }

   @PostMapping(path = "/download")
    public String save(@Valid @ModelAttribute Download download, BindingResult result,  Model model) {
        
        model.addAttribute("materiais", mservice.findAll());
        model.addAttribute("funcionarios", fservice.findAll());        
        model.addAttribute("alunos", aservice.findAll()); 
       
        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("data")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formDownload";
        }
        
        download.setId(null);        
        
        try {
            bmservice.save(download);
            model.addAttribute("msgSucesso", "Download realizado com sucesso.");
            model.addAttribute("download", new Download());
            return "formDownload";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Download", e.getMessage()));
            return "formDownload";
        }
    }
    
      @GetMapping(path = "/download/{id}")
    public String alterar(@PathVariable("id") Long id,Model model) throws NotFoundException {
        model.addAttribute("download", bmservice.findById(id));
        model.addAttribute("materiais", mservice.findAll());
        model.addAttribute("funcionarios", fservice.findAll());        
        model.addAttribute("alunos", aservice.findAll());        
        return "formDownload";
    }
    
    @PostMapping(path = "/download/{id}")
    public String update (@Valid @ModelAttribute Download download, BindingResult result, @PathVariable("id") Long id, Model model) {      

        model.addAttribute("materiais", mservice.findAll());
        model.addAttribute("funcionarios", fservice.findAll());        
        model.addAttribute("alunos", aservice.findAll());        
        
        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("data")) {
                list.add(fe);
            }
        }

        download.setId(id);
        
        try {
            bmservice.save(download);
            model.addAttribute("msgSucesso", "Download atualizar com sucesso.");
            model.addAttribute("download", new Download());
            return "formDownload";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("download", e.getMessage()));
            return "formDownload";
        }
    }
        
     @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) throws NotFoundException {
        bmservice.delete(id);
        return "redirect:/downloads";
    }
}