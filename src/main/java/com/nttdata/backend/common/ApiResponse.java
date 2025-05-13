package com.nttdata.backend.common;

import com.nttdata.backend.exception.ErrorResponse;

public class ApiResponse<T> {
    private T data;
    private ErrorResponse message;

    public ApiResponse(T data) {
        this.data = data;
        ErrorResponse messageOk = new ErrorResponse();
        messageOk.setCode(Error.OK.getCode());
        messageOk.setDescription(Error.OK.getMessage());
        this.message = messageOk; 
    }

    public ApiResponse(T data, ErrorResponse message) {
        this.data = data;
        ErrorResponse messageOk = new ErrorResponse();
        messageOk.setCode(Error.OK.getCode());
        messageOk.setDescription(Error.OK.getMessage());
        this.message = message != null ? message : messageOk;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getMessage() {
        return message;
    }

    public void setMessage(ErrorResponse message) {
        this.message = message;
    }
}
