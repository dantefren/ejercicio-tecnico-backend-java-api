package com.nttdata.backend.exception;

import com.nttdata.backend.common.Error;

public class ServiceException extends RuntimeException {
    private final String code;

    public ServiceException(Error error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    public String getCode() {
        return code;
    }
}
