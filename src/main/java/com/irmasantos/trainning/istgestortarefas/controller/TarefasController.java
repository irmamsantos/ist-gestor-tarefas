package com.irmasantos.trainning.istgestortarefas.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.irmasantos.trainning.istgestortarefas.model.Tarefa;
import com.irmasantos.trainning.istgestortarefas.model.Utilizador;
import com.irmasantos.trainning.istgestortarefas.repository.TarefasRepository;
import com.irmasantos.trainning.istgestortarefas.service.UtilizadoresService;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

	@Autowired
	private TarefasRepository tarefaRepository;
	
	@Autowired
	private UtilizadoresService utilizadoresService;

	@GetMapping("/listar")
	public ModelAndView listar(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/listar");
		String emailUtilizador = request.getUserPrincipal().getName();
		mv.addObject("tarefas", tarefaRepository.carregaTarefasPorUtilizador(emailUtilizador));
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
	public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request) {
		
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
			String emailUtilizador = request.getUserPrincipal().getName();
			Utilizador utilizadorLogado = utilizadoresService.encontrarPorEmail(emailUtilizador);
			tarefa.setUtilizador(utilizadorLogado);
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
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
		tarefaRepository.deleteById(id);
		return "redirect:/tarefas/listar";
	}	
	
	@GetMapping("/concluir/{id}")
	public String concluir(@PathVariable("id") Long id) {
		Tarefa tarefa = tarefaRepository.getOne(id);
		tarefa.setConcluida(true);
		tarefaRepository.save(tarefa);
		return "redirect:/tarefas/listar";
	}
}
