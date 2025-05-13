package com.nttdata.backend.exception;

import com.nttdata.backend.common.Error;

public class ServiceException extends RuntimeException {
    private final String code;
    private final String details;

    // Constructor con Error (enum)
    public ServiceException(Error error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.details = null; // No hay detalles adicionales en este caso
    }

    // Constructor con mensaje y código personalizado
    public ServiceException(String code, String message, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    // Constructor con Error y excepción original
    public ServiceException(Error error, Throwable cause) {
        super(error.getMessage(), cause);
        this.code = error.getCode();
        this.details = cause != null ? cause.getMessage() : null;
    }

    public String getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }
}
