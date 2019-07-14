package com.irmasantos.trainning.istgestortarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.irmasantos.trainning.istgestortarefas.model.Utilizador;
import com.irmasantos.trainning.istgestortarefas.repository.UtilizadoresRepository;

@Service
public class UtilizadoresService {
	
	@Autowired
	private UtilizadoresRepository utilizadoresRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEnconder;

	public Utilizador encontrarPorEmail(String email) {
		return utilizadoresRepository.findByEmail(email);
	}
	
	public void salvar(Utilizador utilizador) {
		utilizador.setSenha(passwordEnconder.encode(utilizador.getSenha()));
		utilizadoresRepository.save(utilizador);
	}
}
