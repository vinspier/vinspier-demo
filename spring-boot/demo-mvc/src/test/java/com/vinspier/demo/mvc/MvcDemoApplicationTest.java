package com.vinspier.demo.mvc;

import com.vinspier.demo.mvc.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MvcDemoApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testTransaction(){
            userService.requiredTransaction();
    }

}
