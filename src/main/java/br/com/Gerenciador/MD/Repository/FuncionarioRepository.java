package br.com.Gerenciador.MD.Repository;

import br.com.Gerenciador.MD.Gerenciamento.Funcionario;
import br.com.Gerenciador.MD.Gerenciamento.Pessoa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository <Funcionario, Long>{
	
    @Query("SELECT p FROM Pessoa p WHERE p.CPF = :CPF OR p.email = :email")
    public List<Pessoa> findByCpfOrEmail(@Param("CPF") String cpf, @Param("email") String email);

    public Funcionario findByEmail(String email);
}