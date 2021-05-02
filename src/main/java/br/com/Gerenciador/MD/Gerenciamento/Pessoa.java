package br.com.Gerenciador.MD.Gerenciamento;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.Gerenciador.MD.Annotation.EmailValidation;
import javax.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	@NotNull
	@Length(max = 150, message = "Nome deve ter no máximo 150 caracteres.")
	private String nome;
        
        @Column(nullable = false, length = 14, unique = true, updatable = true)
        @NotBlank(message = "Telefone obrigatório.")
        @Pattern(regexp = "\\([0-9]{2}\\)[0-9]{4,5}-[0-9]{4}", message = "Padrão deve ser obedecido.")
        @Length(min=13, max = 14, message = "Telefone deve ter 13 ou 14 caracteres (Ex: (99)9999-9999 ou (99)99999-9999")
	private String telefone;

	@NotNull(message = "Email obrigatório, deve conter até 100 caracteres.")
	@Column(nullable = false, length = 100, unique = true, updatable = true)
	@EmailValidation(message = "Email inválido.")
	private String email;

	@Column(nullable = false, length = 14, unique = true, updatable = false)
	@CPF(message = "CPF inválido.")
	private String CPF;

	@Embedded
	@NotNull(message = "Endereço obrigatório.")
	@Valid
	private Endereco endereco;

	public Pessoa() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CPF == null) ? 0 : CPF.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
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

		Pessoa other = (Pessoa) obj;

		if (id != other.id) {
			return false;
		}

		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
