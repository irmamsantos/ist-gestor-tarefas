package com.irmasantos.trainning.istgestortarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.irmasantos.trainning.istgestortarefas.model.Tarefa;

public interface TarefasRepository extends JpaRepository<Tarefa, Long>{
	
	@Query("SELECT t FROM Tarefa t WHERE t.utilizador.email = :emailUtilizador")
	List<Tarefa> carregaTarefasPorUtilizador(@Param("emailUtilizador") String email);

}
