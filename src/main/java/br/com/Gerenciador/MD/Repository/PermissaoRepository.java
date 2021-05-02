package br.com.Gerenciador.MD.Repository;

import br.com.Gerenciador.MD.Gerenciamento.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
    
}
