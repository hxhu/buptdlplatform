package com.bupt.dlplatform.data;

public class GlobalException extends RuntimeException{
    private int code = 400;
    private String message;
    private Object data;
    private String stackMessage;

    public GlobalException() {
    }

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public GlobalException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public GlobalException(int code, String message, Object data, String stackMessage) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
        this.stackMessage = stackMessage;
    }

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    public GlobalException(String message, String stackMessage) {
        super(message);
        this.message = message;
        this.stackMessage = stackMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStackMessage() {
        return stackMessage;
    }

    public void setStackMessage(String stackMessage) {
        this.stackMessage = stackMessage;
    }
}
