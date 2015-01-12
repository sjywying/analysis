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

import com.analysis.web.bean.ManufacturerActive;
import com.analysis.web.bean.User;
import com.analysis.web.service.ManufacturerActiveService;
import com.analysis.web.ui.Menu;

@Controller()
@RequestMapping(value = "/manuActiveStat")
public class ManufacturerActiveController extends AbstractController {

    @Autowired private ManufacturerActiveService manufacturerActiveService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("manuActiveStat/manuActiveStat", new HashMap<String, List<Menu>>());
    }
    
    @RequestMapping(value = "/getByChannel.json", method = RequestMethod.POST)
    public ModelAndView getAllByChannel() {
    	ModelAndView modelAndView = new ModelAndView();
    	
    	Subject subject = SecurityUtils.getSubject();
    	User o = (User)subject.getPrincipal();
        List<ManufacturerActive> list = manufacturerActiveService.findByChannel(o.getChannel());
        List<Object[]> resultList = new ArrayList<Object[]>();
        
        for (ManufacturerActive manufacturerActive : list) {
        	Object[] oarr = new Object[2];
        	oarr[0] = manufacturerActive.getYyyymm();
        	oarr[1] = manufacturerActive.getMnum();
        	resultList.add(oarr);
		}
        
    	modelAndView.addObject("list", resultList);
        return modelAndView;
    }

}
