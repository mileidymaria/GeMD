package br.com.Gerenciador.MD.Services;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Repository.*;
import br.com.Gerenciador.MD.Exception.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MaterialService {

	@Autowired
    private MaterialRepository repository;

    public List<Material> findAll(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repository.findAll(p).toList();
    }

    public List<Material> findAll() {
        return repository.findAll();
    }

    public Material findById(Long id) {
        Optional<Material> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Material não encontrado.");
        }
        return result.get();
    }

    private void salvarArquivo(MultipartFile file, String novoNome) {      
        
        if (file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
        Path dest = Paths.get("uploads", novoNome);
            try {
                file.transferTo(dest);
            } catch (IOException ex) {
                System.out.println("\n erro " + ex );
                throw new RuntimeException("Falha ao salvar o arquivo.");
            }
        } else {
            throw new RuntimeException("Arquivo deve ser do tipo PDF.");
        }
    }    
    
    public Material save(Material m, MultipartFile file) {
      
        //arquivo
        if (file != null) {
            if (!file.isEmpty()) {
                salvarArquivo(file, m.getTitulo() + ".pdf");
                m.setArquivo(m.getTitulo() + ".pdf");
            } else {
                m.setArquivo(null);
            }
        }        
        
        try {
            return repository.save(m);
        } catch (Exception e) {
            throw new RuntimeException("Material não cadastrado.");
        }
    }
    
    public Material update(Material m, MultipartFile file){        
        //arquivo
        if (file != null) {
            if (!file.isEmpty()) {
                salvarArquivo(file, m.getTitulo() + ".pdf");
                m.setArquivo(m.getTitulo() + ".pdf");
            } else {
                m.setArquivo(null);
            }
        }

        try{
            return repository.save(m);
        }catch(Exception e){
            throw new RuntimeException("Falha ao atualizar material.");
        }
    }
    
    public void delete(Long id){
        Material obj = findById(id);
        try{
            repository.delete(obj);
            if (obj.getArquivo() != null) {
                Path caminho = Paths.get("uploads", obj.getArquivo());
                Files.deleteIfExists(caminho);
            }            
        }catch(Exception e){
            throw new RuntimeException("Falha ao deletar material.");
        }
    }	    

    private void verificaExclusaoMaterialComDownloads(Material m) {
        if (!m.getDownloads().isEmpty()) {
            throw new RuntimeException("Material possui downloads futuros. Exclusão não permitida.");
        }
    } 

}
