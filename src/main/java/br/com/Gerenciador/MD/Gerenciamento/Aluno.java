package br.com.Gerenciador.MD.Gerenciamento;

import br.com.Gerenciador.MD.Annotation.LoginValidation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Aluno extends Pessoa {


	@Column(length = 8, unique = true, updatable = true)       
	@LoginValidation(message = "Matrícula obrigatória!")
	private String matricula;

	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Modalidade obrigatória!")
	private TipoModalidadeEnum modalidade;

        @JsonIgnore
        @OneToMany(mappedBy = "aluno")
        @NotNull 
        private List <Download> downloads = new ArrayList<>();         
        
	public Aluno() {
		super();
	}

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }        

        public String getMatricula() {
            return matricula;
        }
                
	public TipoModalidadeEnum getModalidade() {
		return modalidade;
	}

	public void setModalidade(TipoModalidadeEnum modalidade) {
		this.modalidade = modalidade;
	}

        public List<Download> getDownloads() {
            return downloads;
        }

        public void setDownloads(List<Download> downloads) {
            this.downloads = downloads;
        }
        
        

}
