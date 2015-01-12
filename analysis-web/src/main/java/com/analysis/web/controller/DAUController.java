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

import com.analysis.web.bean.DAUNum;
import com.analysis.web.bean.User;
import com.analysis.web.service.DAUService;
import com.analysis.web.ui.Menu;

@Controller()
@RequestMapping(value = "/dau")
public class DAUController extends AbstractController {

    @Autowired private DAUService dauService;
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("dau/dau", new HashMap<String, List<Menu>>());
    }
    
    @RequestMapping(value = "/getAllPVAndUVByChannel.json", method = RequestMethod.POST)
	public ModelAndView getAllPVAndUVByChannel() {
		ModelAndView modelAndView = new ModelAndView();

		Subject subject = SecurityUtils.getSubject();
		User o = (User) subject.getPrincipal();

		List<DAUNum> list = dauService.findAllByChannel(o.getChannel());
		
		List<String> resultCtimeList = new ArrayList<String>();
		List<Integer> resultPVList = new ArrayList<Integer>();
		List<Integer> resultUVList = new ArrayList<Integer>();
		for (DAUNum object : list) {
			resultCtimeList.add(object.getCtime());
			resultPVList.add(object.getPvnum());
			resultUVList.add(object.getUvnum());
		}
		modelAndView.addObject("ctimelist", resultCtimeList);
		modelAndView.addObject("pvlist", resultPVList);
		modelAndView.addObject("uvlist", resultUVList);

		return modelAndView;
	}

    @RequestMapping(value = "/getAllPVByChannel.json", method = RequestMethod.POST)
    public ModelAndView getAllPVByChannel() {
    	ModelAndView modelAndView = new ModelAndView();
    	
    	Subject subject = SecurityUtils.getSubject();
    	User o = (User)subject.getPrincipal();
        List<DAUNum> list = dauService.findAllByChannel(o.getChannel());
        List<Object[]> resultList = new ArrayList<Object[]>();
        
        for (DAUNum object : list) {
        	Object[] oarr = new Object[2];
        	oarr[0] = object.getCtime();
        	oarr[1] = object.getPvnum();
        	resultList.add(oarr);
		}
        
    	modelAndView.addObject("pvnumlist", resultList);
        return modelAndView;
    }
    
    @RequestMapping(value = "/getAllUVByChannel.json", method = RequestMethod.POST)
    public ModelAndView getAllUVByChannel() {
    	ModelAndView modelAndView = new ModelAndView();
    	
    	Subject subject = SecurityUtils.getSubject();
    	User o = (User)subject.getPrincipal();
        List<DAUNum> list = dauService.findAllByChannel(o.getChannel());
        List<Object[]> resultList = new ArrayList<Object[]>();
        
        for (DAUNum object : list) {
        	Object[] oarr = new Object[2];
        	oarr[0] = object.getCtime();
        	oarr[1] = object.getUvnum();
        	resultList.add(oarr);
		}
        
    	modelAndView.addObject("uvnumlist", resultList);
        return modelAndView;
    }

}
