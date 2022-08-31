package com.hongikScheduler.controller;

import com.hongikScheduler.service.QueryProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Slf4j
@RequiredArgsConstructor
public class QueryController {
    private final QueryProcessor queryProcessor;

    @RequestMapping(value ="/test")
    public ModelAndView test(){
        ModelAndView modelAndView =new ModelAndView("index");
        modelAndView.addObject("vso","제발 되라고");
        return modelAndView;
    }
    @RequestMapping(value = "/search")
    public ModelAndView search(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("subjectList",queryProcessor.getAllSubject());
        return modelAndView;
    }
}
