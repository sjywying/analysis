package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.Resource;
import com.analysis.web.bean.User;

/**
 *
 * @author Gavin
 */
public interface UserService {

    User login(String username, String password);
    
    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();
}
