package com.irmasantos.trainning.istgestortarefas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.irmasantos.trainning.istgestortarefas.model.Utilizador;
import com.irmasantos.trainning.istgestortarefas.service.UtilizadoresService;

@Controller
public class ContaController {
	
	@Autowired
	private UtilizadoresService utilizadoresService; 

	@GetMapping("/login")
	public String login() {
		return "conta/login";
	}
	
	@GetMapping("/registration")
	public ModelAndView registrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta/registrar");
		mv.addObject("utilizador", new Utilizador());
		return mv;
	}
	
	@PostMapping("/registration")
	public ModelAndView registrar(@Valid Utilizador utilizador, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		Utilizador usr = utilizadoresService.encontrarPorEmail(utilizador.getEmail());
		if (usr != null) {
			result.rejectValue("email", "", "Utilizador já registrado!");
		}
		if (result.hasErrors()) {
			mv.setViewName("conta/registrar");
			mv.addObject("utilizador", utilizador);
		} else {
			utilizadoresService.salvar(utilizador);
			mv.setViewName("redirect:/login");
		}
		
		return mv;
	}
}
