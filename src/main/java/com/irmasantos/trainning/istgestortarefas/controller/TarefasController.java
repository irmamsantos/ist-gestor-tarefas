package com.irmasantos.trainning.istgestortarefas.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.irmasantos.trainning.istgestortarefas.model.Tarefa;
import com.irmasantos.trainning.istgestortarefas.repository.TarefasRepository;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

	@Autowired
	private TarefasRepository tarefaRepository;

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/listar");
		mv.addObject("tarefas", tarefaRepository.findAll());
		return mv;
	}
	
	@GetMapping("/inserir")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/inserir");
		mv.addObject("tarefa", new Tarefa());
		return mv;		
	}
	
//	@PostMapping("/inserir")
//	public String inserir(Tarefa tarefa) {
//		tarefaRepository.save(tarefa);
//		return "redirect:/tarefas/listar";
//	}
	
	@PostMapping("/inserir")
	public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result) {
		
		ModelAndView mv = new ModelAndView();
		
		if (tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoObrigatoria", "A data de expiração é obrigatória");
		} else if (tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração não pode ser anterior à data actual");
		}
		
		if (result.hasErrors()) {
			mv.setViewName("/tarefas/inserir");
			mv.addObject(tarefa);
		} else {
			tarefaRepository.save(tarefa);
			mv.setViewName("redirect:/tarefas/listar");
		}
		
		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Tarefa tarefa = tarefaRepository.getOne(id);
		mv.setViewName("/tarefas/alterar");
		mv.addObject("tarefa", tarefa);
		return mv;		
	}

	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result) {
		
		ModelAndView mv = new ModelAndView();
		
		if (tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoObrigatoria", "A data de expiração é obrigatória");
		} else if (tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração não pode ser anterior à data actual");
		}
		
		if (result.hasErrors()) {
			mv.setViewName("/tarefas/alterar");
			mv.addObject(tarefa);
		} else {
			tarefaRepository.save(tarefa);
			mv.setViewName("redirect:/tarefas/listar");
		}
		
		return mv;
	}
	
	@GetMapping("/apagar/{id}")
	public String apagar(@PathVariable("id") Long id) {
		tarefaRepository.deleteById(id);
		return "redirect:/tarefas/listar";
	}	
	
}
