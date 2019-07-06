package com.irmasantos.trainning.istgestortarefas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private static final String HOME_ROOT = "/";

    @GetMapping(HOME_ROOT)
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/home");
        mv.addObject("mensagem", "Mensagem do controller");
        return mv;
    }

}