package br.com.Gerenciador.MD.Gerenciamento;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;


@Entity
public class Download implements Serializable{

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonIgnore
    @ManyToOne
    private Funcionario funcionario;

    @NotNull
    @JsonIgnore
    @ManyToOne
    private Aluno aluno;

    @NotNull
    @JsonIgnore
    @ManyToOne
    private Material material; 
    
    @Column(nullable = false, unique = false, updatable = true)
    @PositiveOrZero
    @NotNull
    private int quantidade;    

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Data de início do download é obrigatória.")
    @FutureOrPresent(message = "Data do download deve ser atual ou no futuro, em caso de agendamento.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar data;	
	
    
	public Download() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Funcionario getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public Material getMaterial() {
		return material;
	}


	public void setMaterial(Material material) {
		this.material = material;
	}


	public Calendar getData() {
		return data;
	}


	public void setData(Calendar data) {
		this.data = data;
	}

        public Aluno getAluno() {
            return aluno;
        }

        public void setAluno(Aluno aluno) {
            this.aluno = aluno;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public int getQuantidade() {
            return quantidade;
        } 
        
	


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Download other = (Download) obj;
		if (id != other.id)
			return false;
		return true;
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.funcionario);
        hash = 41 * hash + Objects.hashCode(this.aluno);
        hash = 41 * hash + Objects.hashCode(this.material);
        hash = 41 * hash + this.quantidade;
        hash = 41 * hash + Objects.hashCode(this.data);
        return hash;
    }



}
