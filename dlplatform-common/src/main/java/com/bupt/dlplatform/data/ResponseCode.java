package com.bupt.dlplatform.data;

/**
 * 响应码枚举对象
 * 和http状态码相互独立
 * */

public enum ResponseCode {
    //通用
    OK(2000, "Success"),
    PARAM_INVALID(1000, "缺失参数或无效"),
    //熔断专用
    REMOTE_CALL_FAIL(5000, "服务暂时不可用:"),
    UNKNOWN_EXCEPTION(505, "系统异常"),
    SYSTEM_EXCEPTION(9000,"系统异常"),
    VERIFICATION_CODE_FAILURE(6001,"验证码失效"),
    AUTH_USER_NULL(4006,"人员信息获取失败"),
    THIRD_PARTY_EXCEPTION(3000,"第三方接口异常"),
    VERIFICATION_CODE_EXCEEDING(6004,"验证次数超过上限"),
    VERIFICATION_CODE_ERROR(6002,"验证码错误"),
    AUTH_INVALID_ERROR(4005,"身份验证失败"),
    USER_NOT_EXIST(6003,"用户不存在"),
    //==========业务状态码5500开始,各自约束错误码段=============//
    OPERATE_ERROR(5500, "操作失败"),
    INVALID_PARAM(5002,"输入参数异常"),
    // 4000-4999 权限问题错误code
    AUTH_INVALID(4000, "身份验证失败"),
    AUTH_TOKEN_MISSING(4001, "token缺失"),
    AUTH_TOKEN_INVALID(4002, "token Fail"),
    AUTH_ACCESS_DENIED(4003, "没有权限"),
    JSON_ERROR(5001,"JSON操作异常");

    private final int value;
    private final String description;

    ResponseCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static ResponseCode valueOf(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.value == code) {
                return responseCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
