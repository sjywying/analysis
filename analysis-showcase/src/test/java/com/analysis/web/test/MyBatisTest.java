package com.analysis.web.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.analysis.web.bean.User;
import com.analysis.web.service.UserService;

/**
 *
 * @author Gavin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class MyBatisTest {

    @Autowired
    private UserService userService;

//    @Before
//    public void before() {
//        ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"spring.xml", "spring-mybatis.xml"});
//        userService = (UserService) ac.getBean("userService");
//    }
    @Test
    public void test() {
        List<User> users = userService.getAllUsers();
        System.out.println(users.size());
    }
}
