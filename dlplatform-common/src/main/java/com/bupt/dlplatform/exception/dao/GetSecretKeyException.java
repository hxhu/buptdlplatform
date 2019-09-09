package com.bupt.dlplatform.exception.dao;

import com.bupt.dlplatform.data.GlobalException;

public class GetSecretKeyException extends GlobalException {
    private static final long serialVersionUID = 1L;

    public GetSecretKeyException(String message) {
        super(message);
    }

    public GetSecretKeyException(int code, String message) {
        super(code, message);
    }
}

