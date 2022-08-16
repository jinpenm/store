package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    public JsonResult<Void> handlerException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已使用");
        }
        if (e instanceof PasswordNotMatchException){
            result.setState(4001);
            result.setMessage("密码不正确");
        }
        if (e instanceof USerNotFindException){
            result.setState(4002);
            result.setMessage("用户未找到");
        }
        if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("添加时发生未知错误");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;


    }
}
