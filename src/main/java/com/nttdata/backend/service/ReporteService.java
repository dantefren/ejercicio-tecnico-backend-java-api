package com.nttdata.backend.service;

import com.nttdata.backend.model.Cuenta;
import com.nttdata.backend.model.Movimiento;
import com.nttdata.backend.model.ReporteEstadoCuenta;
import com.nttdata.backend.repository.CuentaRepository;
import com.nttdata.backend.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public ReporteService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    /**
     * Genera un reporte de estado de cuenta para un cliente en un rango de fechas.
     *
     * @param clienteid   ID único del cliente.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin    Fecha de fin del rango.
     * @return ReporteEstadoCuenta con cuentas y movimientos asociados.
     */
    public ReporteEstadoCuenta generarReporte(Instant fechaInicio, Instant fechaFin) {

       
        List<Cuenta> cuentas = cuentaRepository.findAll();
        Map<UUID, List<Movimiento>> movimientosPorCuenta = new HashMap<>();

        for (Cuenta idcuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository.findByidcuenta_IdAndFechaBetween(
                    idcuenta.getId(),
                    fechaInicio,
                    fechaFin
            );
            movimientosPorCuenta.put(idcuenta.getId(), movimientos);
        }

        return new ReporteEstadoCuenta(cuentas, movimientosPorCuenta);
    }
}
