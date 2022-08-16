package com.cy.store.service;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserService {
    /**
     * 用户注册功能
     * @param user 用户注册数据
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username  用户名
     * @param password  密码
     * @return
     */
    User login(String  username,String password);

    Integer ChangePassword(
                            Integer uid,
                            String oldPassword,
                            String newPassword,
                            String modifiedUser
            );


    User  getUserByUid(Integer uid);

    /**
     *
     * @param user 修改的用户数据
     * @return
     */
    Integer UpdateUser(User user);


    void changeAvatar(String avatar,
                      Integer uid,
                      String  modifiedUser) ;
}
