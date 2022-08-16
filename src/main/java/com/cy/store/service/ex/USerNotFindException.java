package com.cy.store.service.ex;

//用户不存在异常
public class USerNotFindException extends ServiceException{
    public USerNotFindException() {
        super();
    }

    public USerNotFindException(String message) {
        super(message);
    }

    public USerNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public USerNotFindException(Throwable cause) {
        super(cause);
    }

    protected USerNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
