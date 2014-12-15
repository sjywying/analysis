package com.analysis.web.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.analysis.web.bean.ActiveNum;
import com.analysis.web.service.ConfigRegActiveService;
import com.analysis.web.service.RegisterService;
import com.analysis.web.ui.Menu;

@Controller()
@RequestMapping(value = "/cractive")
public class ConfigRegActiveController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigRegActiveController.class);

    @Autowired private ConfigRegActiveService configRegActiveService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("cractive/configRegActive", new HashMap<String, List<Menu>>());
    }
    
    @RequestMapping(value = "/getByChannel.json", method = RequestMethod.POST)
    public ModelAndView getByChannel(String channel) {
    	ModelAndView modelAndView = new ModelAndView();
        List<ActiveNum> list = configRegActiveService.findByChannel("100242");
    	modelAndView.addObject("actives", list);
        return modelAndView;
    }

}
