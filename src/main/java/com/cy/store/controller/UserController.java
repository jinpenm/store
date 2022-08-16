package com.cy.store.controller;

import com.cy.store.controller.ex.FileEmptyException;
import com.cy.store.controller.ex.FileSizeException;
import com.cy.store.controller.ex.FileTypeException;
import com.cy.store.controller.ex.FileUploadIOException;
import com.cy.store.entity.BaseEntity;
import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController //@Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping("reg")
    /*public JsonResult<Void> reg(User user){
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名已使用");
        }catch (InsertException e){
            result.setState(5000);
            result.setMessage("添加时发生未知错误");
        }
        return result;
    }*/
    public JsonResult<Void> reg(User user){
        JsonResult<Void> result = new JsonResult<>();
        userService.reg(user);
        result.setState(OK);
        result.setMessage("成功");
        return result;
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){

        User login = userService.login(username, password);
        JsonResult<User> result = new JsonResult<>();
        result.setState(OK);
        result.setMessage("登录成功");
        result.setData(login);
        session.setAttribute("username",username);
        session.setAttribute("Uid",result.getData().getUid());
        return result;
    }

    @RequestMapping("change_Password")
    public JsonResult<Void> changePassword(String oldPassword,
                               String newPassword,
                               HttpSession session
                               ) {
        Object uid = session.getAttribute("Uid");
        Object username = session.getAttribute("username");
        userService.ChangePassword((Integer) uid,oldPassword,newPassword,username.toString());
        return new JsonResult<>(OK);
    }


    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getUserByUid((Integer) session.getAttribute("Uid"));
        return new JsonResult<>(OK,data);

    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(HttpSession session,
                                       User user){
        Object uid = session.getAttribute("Uid");
        user.setUid((Integer)uid);

        Integer rows = userService.UpdateUser(user);
        System.out.println(rows);
        return new JsonResult<Void>(OK);
    }

    public final static int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    public final static List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/bmp");
    }


@RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                         MultipartFile file){


        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件过大");
        }
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不匹配");
        }
        String parent = session.getServletContext().getRealPath("upload");
        System.out.println(parent);
        //创建一个File对象指向这个要保存的目录
        File dir = new File(parent);
        //判断这个目录是否存在
        if (!dir.exists()){
            dir.mkdir();//不存在就创建
        }

        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        //创建一个新的文件名（UUID）防止文件名重复替换掉原文件
        String filename = UUID.randomUUID().toString()+suffix;

        //在目录中创建一个新文件的空文件
        File dest = new File(dir, filename);

        try {
            file.transferTo(dest);//把file写入dest中
        } catch (IOException e) {
            throw new FileUploadIOException("IO异常");
        }
        Object uid = session.getAttribute("Uid");

        Object username = session.getAttribute("username");


        String avatar = "/upload/" + filename;
        userService.changeAvatar(avatar,(Integer) uid,(String) username);

        return new JsonResult<>(OK,avatar);
    }

}
