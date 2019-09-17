package com.bupt.dlplatform.exception.dao;

import com.bupt.dlplatform.data.ResponseCode;

/**
 * @author LeoLv
 * @date 2019/9/17 10:38
 */
public class ApiException extends ExpenseException{

    public ApiException() {
    }

    // 异常码
    private int code;

    // 异常信息(和responseVo字段msg名称一致,避免丢失异常信息)
    private String message;

    public ApiException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getDescription(), cause);
        this.code = responseCode.value();
        this.message = responseCode.getDescription();
    }

    public ApiException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.code = responseCode.value();
        this.message = responseCode.getDescription();
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;

    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
