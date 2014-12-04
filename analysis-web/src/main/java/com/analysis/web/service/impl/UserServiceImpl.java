package com.analysis.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysis.web.bean.User;
import com.analysis.web.bean.UserExample;
import com.analysis.web.common.util.Md5Utils;
import com.analysis.web.dao.UserMapper;
import com.analysis.web.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUsernameEqualTo(username);
		List<User> users = userMapper.selectByExample(userExample);
		if (users != null && users.size() == 1) {
			user = users.get(0);
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return userMapper.selectByExample(null);
	}

	@Override
	public User login(String username, String password) {
		User user = getUserByUsername(username);
		if (Md5Utils.hash(username + password + user.getSalt()).equals(
				user.getPassword())) {
			return user;
		}
		return null;
	}

}
