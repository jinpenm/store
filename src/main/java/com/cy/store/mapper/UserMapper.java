package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserMapper {

    /**
     * 插入用户数据
     * @param user
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
     * 根据Uid查询
     * @param uid
     * @return
     */
    User findByUid(Integer uid);


    /**
     * 根据Uid修改密码
     * @param password
     * @param uid
     * @return
     */
    Integer updatePasswordByUid(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);


    /**
     * 更新用户信息
     * @param user  用户的信息
     * @return  数据库受影响的行数
     */
    Integer updateInfoByUid(User user);

    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime,
                              @Param("avatar") String avatar);


}
