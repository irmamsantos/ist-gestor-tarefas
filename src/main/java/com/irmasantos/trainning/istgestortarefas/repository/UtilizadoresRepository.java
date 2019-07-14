package com.irmasantos.trainning.istgestortarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irmasantos.trainning.istgestortarefas.model.Utilizador;

public interface UtilizadoresRepository extends JpaRepository<Utilizador, Long>{

	Utilizador findByEmail(String email);
}
