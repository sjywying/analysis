package com.analysis.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.analysis.web.service.UserService;

@Controller
public class SecurityController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 若通过地址栏主动进入login页面，则可发生死循环（一直登录不上），故退出。
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView fail() {
        return new ModelAndView("login");
    }

    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }
        return "redirect:/login";
    }

}
