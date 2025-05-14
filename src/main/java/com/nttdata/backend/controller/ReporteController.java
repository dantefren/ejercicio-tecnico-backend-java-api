package com.nttdata.backend.controller;

import com.nttdata.backend.common.ApiResponse;
import com.nttdata.backend.model.ReporteEstadoCuenta;
import com.nttdata.backend.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ReporteEstadoCuenta>> obtenerEstadoCuenta(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {


        Instant fechaInicioI = fechaInicio.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Instant fechaFinI = fechaFin.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

        ReporteEstadoCuenta reporte = reporteService.generarReporte(fechaInicioI, fechaFinI);
        return ResponseEntity.ok(new ApiResponse<>(reporte));
    }
}
