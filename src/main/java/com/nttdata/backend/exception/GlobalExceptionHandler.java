package com.nttdata.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.nttdata.backend.common.ApiResponse;
import com.nttdata.backend.common.Error;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Object>> handleServiceException(ServiceException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ex.getCode());
        errorResponse.setDescription(ex.getMessage());
        errorResponse.setDetails(request.getDescription(false));
        errorResponse.setError(ex.getDetails() != null ? ex.getDetails() : ex.toString());

        ApiResponse<Object> response = new ApiResponse<>(null, errorResponse); // `data` es null en errores
        int statusCode = parseStatusCode(ex.getCode());

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(Error.HA_OCURRIDO_UN_ERROR_INESPERADO.getCode());
        errorResponse.setDescription(Error.HA_OCURRIDO_UN_ERROR_INESPERADO.getMessage());
        errorResponse.setDetails(request.getDescription(false));
        errorResponse.setError(ex.toString());

        ApiResponse<Object> response = new ApiResponse<>(null, errorResponse);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private int parseStatusCode(String code) {
        try {
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return HttpStatus.BAD_REQUEST.value();
        }
    }
}
