package com.nttdata.backend.controller;

import com.nttdata.backend.service.MovimientoService;
import com.nttdata.backend.model.Movimiento;
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;
import com.nttdata.backend.common.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Movimiento>> createMovimiento(@RequestBody Movimiento movimiento) {
        Movimiento newMovimiento = movimientoService.createMovimiento(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(newMovimiento, null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Movimiento>> registrarMovimiento(@RequestParam UUID idCuenta, @RequestParam double monto) {
        Movimiento movimiento = movimientoService.registrarMovimiento(idCuenta, monto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(movimiento));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Movimiento>> getMovimientoById(@PathVariable UUID id) {
        Optional<Movimiento> movimiento = movimientoService.getMovimientoById(id);
        return movimiento.map(m -> ResponseEntity.ok(new ApiResponse<>(m, null)))
                         .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Movimiento>>> getAllMovimientos() {
        return ResponseEntity.ok(new ApiResponse<>(movimientoService.getAllMovimientos(), null));
    }

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<ApiResponse<List<Movimiento>>> getMovimientosByCuenta(@PathVariable UUID idCuenta) {
        List<Movimiento> movimientos = movimientoService.getMovimientosByCuentaId(idCuenta);
        return ResponseEntity.ok(new ApiResponse<>(movimientos, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Movimiento>> updateMovimiento(@PathVariable UUID id, @RequestBody Movimiento updatedMovimiento) {
        Movimiento movimientoUpdated = movimientoService.updateMovimiento(id, updatedMovimiento);
        return ResponseEntity.ok(new ApiResponse<>(movimientoUpdated, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMovimiento(@PathVariable UUID id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.ok(new ApiResponse<>(null, null));
    }
}
