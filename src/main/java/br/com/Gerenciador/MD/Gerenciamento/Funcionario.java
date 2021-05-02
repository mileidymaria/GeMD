package br.com.Gerenciador.MD.Gerenciamento;

import javax.validation.constraints.NotNull;
import br.com.Gerenciador.MD.Annotation.LoginValidation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(value = "senha", allowGetters = false, allowSetters = true)
@Entity
public class Funcionario extends Pessoa{
    
    @Column(nullable = false)
    @NotBlank(message = "Senha obrigatória.")   
    private String senha;
	
    @JsonIgnore
    @OneToMany(mappedBy = "funcionario")
    @NotNull
    private List <Download> downloads = new ArrayList<>();   
    
    @ManyToMany(fetch = FetchType.EAGER)
    @Size(min = 1, message = "Funcionário deve ter no mínimo 1 permissão.")
    private List<Permissao> permissoes = new ArrayList<>();    
    
    
	public Funcionario() {
		super();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Download> getDownloads() {
		return downloads;
	}

	public void setDownloads(List<Download> downloads) {
		this.downloads = downloads;
	}

	public List<Permissao> getPermissoes() {
            return permissoes;
        }

        public void setPermissoes(List<Permissao> permissoes) {
            this.permissoes = permissoes;
        }
}
