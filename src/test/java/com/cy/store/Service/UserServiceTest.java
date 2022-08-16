package com.cy.store.Service;

import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("timi001");
            user.setPassword("001");
            userService.reg(user);
        } catch (SecurityException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        try {
            User user = new User();
            user.setUsername("timi001");
            user.setPassword("001");
            User login = userService.login(user.getUsername(), user.getPassword());
            System.out.println(login);
        } catch (SecurityException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void changePassword(){
        try {
            String username = "root";
            Integer rows = userService.ChangePassword(8, "001", "0001", username);
            System.out.println(rows);
        } catch (SecurityException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }

    }


    @Test
    public void updateTest(){
        User user = new User();
        user.setUid(8);
        user.setUsername("timi001");
        user.setEmail("123@qq.com");
        user.setPhone("13719631221");
        user.setGender(1);
        try {
            Integer rows = userService.UpdateUser(user);
            System.out.println(rows);
        } catch (SecurityException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }

    }
}
