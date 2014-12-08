package com.analysis.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysis.web.bean.Role;
import com.analysis.web.mapper.RoleMapper;
import com.analysis.web.service.RoleService;

/**
 *
 * @author Gavin
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

}
