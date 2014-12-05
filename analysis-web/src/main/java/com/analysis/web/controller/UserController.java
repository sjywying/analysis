package com.analysis.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analysis.web.bean.User;
import com.analysis.web.service.UserService;

@Controller
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

}
