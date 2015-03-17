package com.analysis.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.analysis.web.bean.Manufacturer;
import com.analysis.web.bean.User;
import com.analysis.web.service.ManufacturerService;
import com.analysis.web.ui.Menu;

@Controller()
@RequestMapping(value = "/manufacturer")
public class ManufacturerController extends AbstractController {

    @Autowired private ManufacturerService manufacturerService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("manufacturer/manufacturer", new HashMap<String, List<Menu>>());
    }
    
    @RequestMapping(value = "/getByChannel.json", method = RequestMethod.POST)
    public ModelAndView getAllByChannel() {
    	ModelAndView modelAndView = new ModelAndView();
    	
    	Subject subject = SecurityUtils.getSubject();
    	User o = (User)subject.getPrincipal();
        List<Manufacturer> list = manufacturerService.findByChannel(o.getChannel());

        List<String> cdates = new ArrayList<String>();
        List<Long> regnum = new ArrayList<Long>();
        List<Long> activenum = new ArrayList<Long>();
        
        for (Manufacturer manufacturer : list) {
            cdates.add(manufacturer.getCdate());
            regnum.add(manufacturer.getRegnum());
            activenum.add(manufacturer.getActivenum());
		}

        modelAndView.addObject("cdate", cdates);
    	modelAndView.addObject("regnum", regnum);
        modelAndView.addObject("activenum", activenum);
        return modelAndView;
    }

}
