package egox.sm.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egox.sm.bean.Active;
import egox.sm.service.ActiveService;
import egox.sm.ui.Menu;

/**
 *
 * @author Gavin <egox.vip@gmail.com>
 */
@Controller()
@RequestMapping(value = "/activestat")
public class ActiveStatController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ActiveStatController.class);

    @Autowired
    private ActiveService activeService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
    	System.out.println("index");
        return new ModelAndView("activestat/active", new HashMap<String, List<Menu>>());
    }
    
    @RequestMapping(value = "/getAllByChannel.json", method = RequestMethod.POST)
    public ModelAndView getAllByChannel() {
    	ModelAndView modelAndView = new ModelAndView();
    	Subject subject = SecurityUtils.getSubject();
        List<Active> list = activeService.findByChannelAndModel("100322");
    	modelAndView.addObject("actives", list);
        return modelAndView;
    }

}
