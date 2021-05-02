package br.com.Gerenciador.MD.Gerenciamento;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Embeddable
public class Endereco implements Serializable{
	
    private static final long serialVersionUID = 1L;
	@Column(length = 9)
	@NotNull
	@Length(min = 9, max = 9, message = "CEP deve ter exatamente 9 caracteres (Ex: 99999-999).")
	private String CEP;

	@Column(length = 200, nullable = false)
	@NotBlank(message = "Rua obrigatória.")
	@Length(max = 200, message = "Rua deve ter no máximo 200 caracteres.")
	private String rua;

	@Column(length = 50, nullable = false)
	@NotBlank(message = "Bairro obrigatório.")
	@Length(max = 50, message = "Bairro deve ter no máximo 50 caracteres.")
	private String bairro;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "Cidade obrigatória.")
	@Length(max = 50, message = "Cidade deve ter no máximo 50 caracteres.")
	private String cidade;

	@Column(nullable = false)
	@Digits(integer = 4, fraction = 0, message = "Número deve ser inteiro e ter até 4 digitos.")
	private int numero;

	public Endereco() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CEP == null) ? 0 : CEP.hashCode());
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + numero;
		result = prime * result + ((rua == null) ? 0 : rua.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (CEP == null) {
			if (other.CEP != null)
				return false;
		} else if (!CEP.equals(other.CEP))
			return false;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (numero != other.numero)
			return false;
		if (rua == null) {
			if (other.rua != null)
				return false;
		} else if (!rua.equals(other.rua))
			return false;
		return true;
	}


	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
