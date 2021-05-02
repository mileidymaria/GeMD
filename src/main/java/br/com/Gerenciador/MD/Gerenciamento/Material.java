package br.com.Gerenciador.MD.Gerenciamento;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

@Entity
public class Material implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Long id;

    @Column(length = 35)
    @NotNull   
    private String titulo;

    @Column(length = 200)
    @Length(max = 200, message = "Arquivo deve ter no máximo 200 caracteres.")    
    private String arquivo;    
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoModalidadeEnum modalidade;

    @PositiveOrZero
    @Column(length = 5)
    @NotNull(message = "Preco obrigatório.")	
    private double preco; 
    
    @JsonIgnore
    @OneToMany(mappedBy = "material")
    @NotNull
    private List <Download> downloads = new ArrayList<>();     
    
	public Material() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoModalidadeEnum getModalidade() {
		return modalidade;
	}

	public void setModalidade(TipoModalidadeEnum modalidade) {
		this.modalidade = modalidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public void setDownloads(List<Download> downloads) {
            this.downloads = downloads;
        }

        public List<Download> getDownloads() {
            return downloads;
        }

        public void setArquivo(String arquivo) {
            this.arquivo = arquivo;
        }

        public String getArquivo() {
            return arquivo;
        }        

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Material other = (Material) obj;
            return Objects.equals(this.id, other.id);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + Objects.hashCode(this.id);
            hash = 97 * hash + Objects.hashCode(this.titulo);
            hash = 97 * hash + Objects.hashCode(this.arquivo);
            hash = 97 * hash + Objects.hashCode(this.modalidade);
            hash = 97 * hash + (int) (Double.doubleToLongBits(this.preco) ^ (Double.doubleToLongBits(this.preco) >>> 32));
            hash = 97 * hash + Objects.hashCode(this.downloads);
            return hash;
        }




}
