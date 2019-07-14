package com.irmasantos.trainning.istgestortarefas.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="usr_utilizadores")
public class Utilizador {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="usr_id")
	private Long id;
	
	@Column(name="usr_email", length=100, nullable=false)
	@NotNull(message="O Email é obrigatório!")
	@Length(max=100, min=3, message="O Email deve conter entre 3 e 100 caracteres")	
	private String email;
	
	@Column(name="usr_senha", length=100, nullable=false)
	@NotNull(message="A senha é obrigatória!")
	private String senha;
	
	@OneToMany(mappedBy="utilizador", fetch=FetchType.LAZY)
	private List<Tarefa> tarefas;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Utilizador other = (Utilizador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
