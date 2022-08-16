package com.cy.store.mapper;


import com.cy.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void insert(){
        User user = new User();
        user.setUsername("timi");
        user.setPassword("123");
        Integer insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void select(){
        String username = "timi";
        User byUsername = userMapper.findByUsername(username);
        System.out.println(byUsername);
    }


    @Test
    public void selectbyUid(){
        User byUid = userMapper.findByUid(1);
        System.out.println(byUid);
    }

    @Test
    public void updatePassword(){
        Integer integer = userMapper.updatePasswordByUid(1, "123456", "管理员", new Date());
        System.out.println(integer);
    }


}
