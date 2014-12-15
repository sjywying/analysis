package com.analysis.web.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.analysis.web.bean.ActiveNum;
import com.analysis.web.bean.User;
import com.analysis.web.service.ActiveService;
import com.analysis.web.ui.Menu;

@Controller()
@RequestMapping(value = "/activestat")
public class ActiveStatController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ActiveStatController.class);

    @Autowired
    private ActiveService activeService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("activestat/active", new HashMap<String, List<Menu>>());
    }
    
    @RequestMapping(value = "/getAllByChannel.json", method = RequestMethod.POST)
    public ModelAndView getAllByChannel() {
    	ModelAndView modelAndView = new ModelAndView();
    	Subject subject = SecurityUtils.getSubject();
    	User o = (User)subject.getPrincipal();
        List<ActiveNum> list = activeService.findByChannel(o.getChannel());
    	modelAndView.addObject("actives", list);
        return modelAndView;
    }

}
