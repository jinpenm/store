package com.cy.store.service.inpl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;



    @Override
    public void reg(User user) {


        User result = userMapper.findByUsername(user.getUsername());
        if (result != null){
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //待加密的旧密码
        String oldPassword = user.getPassword();
        //盐值
        String salt = UUID.randomUUID().toString().toUpperCase();

        String md5Password = getMD5Password(oldPassword, salt);

        user.setSalt(salt);
        user.setPassword(md5Password);

        //补全是否被删除字段数据
        user.setIsDelete(0);

        //补全4个日志数据
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);


        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("在用户注册过程中出现未知错误");
        }

    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);


        if (result == null || !result.getIsDelete().equals(0)){
            throw new UsernameDuplicatedException("用户不存在");
        }

        String md5Password = getMD5Password(password, result.getSalt());

        if (!md5Password.equals(result.getPassword())){
            throw new PasswordNotMatchException("密码不匹配");
        }


        //重新创建一个新的User对象
        //只封装这三个属性使它的体量变小提高系统性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;

    }

    @Override
    public Integer ChangePassword(Integer uid,
                                  String oldPassword,
                                  String newPassword,
                                  String modifiedUser) {

        User result = userMapper.findByUid(uid);

        if (result == null||result.getIsDelete()==1){
            throw new USerNotFindException("没找到该账号");
        }
        String salt = result.getSalt();
        String md5Password = getMD5Password(oldPassword, salt);
        if (!md5Password.equals(result.getPassword())){
            throw new PasswordNotMatchException("密码错误");
        }
        newPassword = getMD5Password(newPassword,salt);
        Integer rows = userMapper.updatePasswordByUid(uid, newPassword, modifiedUser, new Date());
        if (rows != 1){
            throw new UpdateException("修改失败");
        }
        return rows;

    }

    @Override
    public User getUserByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null||result.getIsDelete()==1){
            throw new USerNotFindException("没找到该账号");
        }
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());
        user.setGender(result.getGender());
        return user;
    }


    public Integer UpdateUser(User user){
        User result = userMapper.findByUid(user.getUid());
        if (result == null||result.getIsDelete()==1){
            throw new USerNotFindException("没找到该账号");
        }
        user.setModifiedUser(result.getUsername());

        user.setModifiedTime(new Date());


        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new UpdateException("用户资料修改异常");
        }
        return rows;
    }

    @Override
    public void changeAvatar(String avatar, Integer uid, String modifiedUser) {
        User result = userMapper.findByUid(uid);
        if (result == null||result.getIsDelete()==1){
            throw new USerNotFindException("没找到该账号");
        }
        Integer rows = userMapper.updateAvatarByUid(uid,modifiedUser,new Date(),avatar);
        if (rows != 1){
            throw new UpdateException("头像修改异常");
        }
    }


    private String getMD5Password(String Password,String salt){
        for (int i = 0; i<3 ;i++){
            Password =
                DigestUtils.md5DigestAsHex((salt + Password + salt).getBytes()).toString().toUpperCase();
        }
        return Password;
    }
}
