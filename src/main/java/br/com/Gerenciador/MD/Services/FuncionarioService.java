package br.com.Gerenciador.MD.Services;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Repository.*;
import br.com.Gerenciador.MD.Exception.*;

import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

	@Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> findAll(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repository.findAll(p).toList();
    }

    public List<Funcionario> findAll() {
        return repository.findAll();
    }
    
    public Funcionario findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Funcionario findById(Long id) {
        Optional<Funcionario> result = repository.findById(id);
        if (result == null) {
            throw new NotFoundException("Funcionario não encontrado.");
        }
        return result.get();
    }

    public Funcionario save(Funcionario f) {
    	
        verificaCpfEmailCadastrado(f.getCPF(), f.getEmail());
        try {
            f.setSenha(new BCryptPasswordEncoder().encode(f.getSenha()));
            return repository.save(f);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar o Funcionario.");
        }
    }

    public Funcionario update(Funcionario f, String senhaAtual, String novaSenha, String confirmarNovaSenha) {
        
        Funcionario obj = findById(f.getId());
        alterarSenha(obj, senhaAtual, novaSenha, confirmarNovaSenha);
        try {
            f.setCPF(obj.getCPF());
            f.setEmail(obj.getEmail());
            f.setSenha(obj.getSenha());
            return repository.save(f);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar o Funcionario.");
        }
    }

    public void delete(Long id) {
        Funcionario obj = findById(id);
        verificaExclusaoFuncionarioComDownloads(obj);
        try {
        	repository.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao excluir o Funcionario");
        }

    }

    private void verificaCpfEmailCadastrado(String cpf, String email) {
        List<Pessoa> result = repository.findByCpfOrEmail(cpf, email);
        if (!result.isEmpty()) {
            throw new RuntimeException("CPF ou EMAIL já cadastrados.");
        }
    }

    private void alterarSenha(Funcionario obj, String senhaAtual, String novaSenha, String confirmarNovaSenha) {
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        if (!senhaAtual.isBlank() && !novaSenha.isBlank() && !confirmarNovaSenha.isBlank()) {
            if (!crypt.matches(senhaAtual, obj.getSenha())) {
                throw new RuntimeException("Senha atual está incorreta.");
            }
            if (!novaSenha.equals(confirmarNovaSenha)) {
                throw new RuntimeException("As senhas não conferem.");
            }
            obj.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
        }
    }

    public void removePermissoesNulas(Funcionario f) {
        f.getPermissoes().removeIf((Permissao p) -> {
            return p.getId() == null;
        });
        if (f.getPermissoes().isEmpty()) {
            throw new RuntimeException("Funcionario deve conter no mínimo 1 permissão.");
        }
    }    
    
    private void verificaExclusaoFuncionarioComDownloads(Funcionario f) {
        if (!f.getDownloads().isEmpty()) {
            throw new RuntimeException("Funcionario possui downloads. Exclusão não permitida.");
        }
    }    
	
}
