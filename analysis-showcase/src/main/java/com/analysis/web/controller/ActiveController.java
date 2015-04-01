package com.analysis.web.controller;

import com.analysis.web.bean.ActiveNum;
import com.analysis.web.bean.User;
import com.analysis.web.service.ActiveService;
import com.analysis.web.ui.Menu;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller()
@RequestMapping(value = "/active")
public class ActiveController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ActiveController.class);

    SimpleDateFormat simpleDateFormat =   new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private ActiveService activeService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("stat/active", new HashMap<String, List<Menu>>());
    }
    
    @RequestMapping(value = "/getAllByChannel.json", method = RequestMethod.POST)
    public ModelAndView getAllByChannel() {
    	ModelAndView modelAndView = new ModelAndView();
    	Subject subject = SecurityUtils.getSubject();
    	User o = (User)subject.getPrincipal();
        List<ActiveNum> list = activeService.findByChannel(o.getChannel());

        List<List<Long>> result = new ArrayList<List<Long>>();
//        List<List<Long>> result1 = new ArrayList<List<Long>>();
        for (Iterator<ActiveNum> iterator = list.iterator(); iterator.hasNext(); ) {
            ActiveNum next = iterator.next();
            List<Long> element = new ArrayList<Long>();
//            List<Long> element2 = new ArrayList<Long>();
            try {
                element.add((simpleDateFormat.parse(String.valueOf(next.getAdate()))).getTime());
                element.add((long)next.getNum());
                result.add(element);

//                element2.add((simpleDateFormat.parse(String.valueOf(next.getAdate()))).getTime());
//                element2.add((long)next.getNum());
//                result1.add(element2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        modelAndView.addObject("actives", list);
        modelAndView.addObject("result", result);
//        modelAndView.addObject("reg", result1);
        return modelAndView;
    }

}
