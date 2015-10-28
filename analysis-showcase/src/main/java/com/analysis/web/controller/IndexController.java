package com.analysis.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.analysis.web.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.analysis.web.service.ResourceService;
import com.analysis.web.ui.Menu;

@Controller
public class IndexController extends AbstractController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
//        List<Resource> resources = resourceService.getResourcesByUserId(1L);
//        List<Menu> menus = resourceService.convetToMenus(resources);
//        Map<String, List<Menu>> map = new HashMap<String, List<Menu>>();
//        map.put("sidenav", menus);/manufacturer/index
        Subject subject = SecurityUtils.getSubject();
        User o = (User)subject.getPrincipal();
//        if("101702".equals(o.getChannel()) || "100692".equals(o.getChannel())) {
//            return new ModelAndView("redirect:/manufacturer/index", new HashMap<String, List<Menu>>());
//        } else {
            return new ModelAndView("index/index_", new HashMap<String, List<Menu>>());
//        }
    }

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public ModelAndView welcome() {
    	return new ModelAndView("index/index_", new HashMap<String, List<Menu>>());
//        return "redirect:/index/index_";
    }

}
