package com.nttdata.backend.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa el reporte de estado de cuenta de un cliente,
 * incluyendo sus cuentas y movimientos asociados en un rango de fechas.
 */
@Getter
@Setter
public class ReporteEstadoCuenta {
    private String clienteid;
    private List<Cuenta> cuentas;
    private Map<UUID, List<Movimiento>> movimientosPorCuenta;

    public ReporteEstadoCuenta(List<Cuenta> cuentas, Map<UUID, List<Movimiento>> movimientosPorCuenta) {
        this.cuentas = cuentas;
        this.movimientosPorCuenta = movimientosPorCuenta;
    }
}
