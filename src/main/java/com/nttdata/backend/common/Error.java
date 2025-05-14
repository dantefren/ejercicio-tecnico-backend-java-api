package com.nttdata.backend.common;

public enum Error {

    //DTACO: Control de errores generales
    OK("000", "OK"),
    SOLICITUD_CON_PARAMETROS_INVALIDOS("400", "Solicitud con parámetros inválidos"),
    ACCESO_NO_AUTORIZADO("401", "Acceso no autorizado"),
    SIN_PERMISOS_PARA_REALIZAR_ESTA_ACCION("403", "No tienes permiso para realizar esta acción"),
    RECURSO_NO_ENCONTRADO("404", "Recurso no encontrado"),
    HA_OCURRIDO_UN_ERROR_INESPERADO("500", "Ocurrió un error inesperado en el sistema"),
    CONFLICTO_CONCURRENCIA ("001", "Error de Concurrencia"),
    SALDO_NO_DISPONIBLE ("002","Saldo no disponible");


    private final String code;
    private final String message;

    Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
