package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.Resource;
import com.analysis.web.ui.Menu;

/**
 *
 * @author Gavin
 */
public interface ResourceService {
    List<Resource> getResourcesByUserId(Long id);
    
    Resource getResourceById(Long id);
    
    List<Resource> getAllResources();
    
    public List<Menu> convetToMenus(List<Resource> resources);
}
