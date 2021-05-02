package br.com.Gerenciador.MD.Services;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Repository.*;
import br.com.Gerenciador.MD.Exception.*;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AlunoService {

	@Autowired
    private AlunoRepository repository;

    public List<Aluno> findAll(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repository.findAll(p).toList();
    }

    public List<Aluno> findAll() {
        return repository.findAll();
    }

    public Aluno findById(Long id) {
        Optional<Aluno> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Aluno não encontrado.");
        }
        return result.get();
    }

    public Aluno save(Aluno a) {

        verificaCpfEmailCadastrado(a.getCPF(), a.getEmail());
        try {
            return repository.save(a);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao cadastrar aluno.");
        }
    }

    public Aluno update(Aluno a) {
        //Aluno existente
    	Aluno obj = findById(a.getId());
        //arquivo
        a.setCPF(obj.getCPF());
        a.setEmail(obj.getEmail());
        try {
            a.setCPF(obj.getCPF());
            a.setEmail(obj.getEmail());
            return repository.save(a);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao atualizar aluno.");
        }
    }

    public void delete(Long id) {
        Aluno obj = findById(id);
        try {
            repository.delete(obj);
            if (obj.getCPF() != null) {
                Path caminho = Paths.get("uploads", obj.getCPF());
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao excluir aluno.");
        }
    }


    private void verificaCpfEmailCadastrado(String cpf, String email) {
        List<Pessoa> result = repository.findByCpfOrEmail(cpf, email);
        if (!result.isEmpty()) {
            throw new RuntimeException("CPF ou EMAIL já cadastrados.");
        }
    } 
    
    private void verificaExclusaoAlunoComDownloads(Aluno a) {
        if (!a.getDownloads().isEmpty()) {
            throw new RuntimeException("Aluno possui downloads a serem realizados. Exclusão não permitida.");
        }
    }     
    
}
