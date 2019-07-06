package com.irmasantos.trainning.istgestortarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irmasantos.trainning.istgestortarefas.model.Tarefa;

public interface TarefasRepository extends JpaRepository<Tarefa, Long>{

}
