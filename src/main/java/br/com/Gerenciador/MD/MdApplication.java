package br.com.Gerenciador.MD;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Repository.*;

 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class MdApplication implements CommandLineRunner {

    	@Autowired
    private AlunoRepository alunoRepo;
    @Autowired
    private FuncionarioRepository funcionarioRepo;
    @Autowired
    private MaterialRepository materialRepo;
    @Autowired
    private DownloadRepository baixaMaterialRepo;	
    @Autowired
    private PermissaoRepository permissaoRepo;	    
    
	public static void main(String[] args) {
		SpringApplication.run(MdApplication.class, args);
	}

            @Override
	public void run(String... args) throws Exception {
	    // TODO Auto-generated method stub
	
            // Permissão
            
            Permissao p1 = new Permissao();
            p1.setNome("FUNCIONÁRIO");
            
            Permissao p2 = new Permissao();
            p2.setNome("ADMIN");            
            permissaoRepo.saveAll(List.of(p1, p2));
            
	    //Endereco
            Endereco endereco1 = new Endereco();
            endereco1.setRua("Av. Pelinca");
            endereco1.setNumero(24);
            endereco1.setBairro("Parque Tamandare");
            endereco1.setCidade("Campos dos Goytacazes");
            endereco1.setCEP("28035-175");		

            Endereco endereco2 = new Endereco();
            endereco2.setRua("Av. Tancredo Neves");
            endereco2.setNumero(101);
            endereco2.setBairro("Parque Jardim Carioca");
            endereco2.setCidade("Campos dos Goytacazes");
            endereco2.setCEP("28035-336");            

            //Funcionario
            Funcionario f1 = new Funcionario();
            f1.setNome("admin");
            f1.setPermissoes(List.of(p1,p2));
            f1.setEmail("administrador@gmail.com");
            f1.setCPF("918.361.440-08");
            f1.setEndereco(endereco1);
            f1.setTelefone("(22)99711-2112");
            f1.setSenha(new BCryptPasswordEncoder().encode("12345678"));
            funcionarioRepo.save(f1);	
		
            Material m1 = new Material();
            m1.setTitulo("FAIRY: THE GOOD BEAR");
            m1.setModalidade(TipoModalidadeEnum.KIDS);
            m1.setPreco(28.36);
            materialRepo.save(m1);

            Material m2 = new Material();
            m2.setTitulo("A DROP IN THE OCEAN");
            m2.setModalidade(TipoModalidadeEnum.MASTER);
            m2.setPreco(56.78);
            materialRepo.save(m2);    
            
            Aluno a1 = new Aluno();
            a1.setNome("TESTE JULIANA SOUZA");
            a1.setEmail("jusouza@gmail.com");
            a1.setCPF("729.941.620-40");
            a1.setEndereco(endereco2);
            a1.setTelefone("(22)99711-2512");
            a1.setMatricula("1234SALV");
            a1.setModalidade(TipoModalidadeEnum.MASTER);
            alunoRepo.save(a1);
        }        
        
}
