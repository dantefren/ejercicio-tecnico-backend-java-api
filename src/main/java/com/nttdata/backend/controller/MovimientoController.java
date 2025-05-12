package com.nttdata.backend.controller;

import com.nttdata.backend.service.MovimientoService;
import com.nttdata.backend.model.Movimiento;
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;
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
    public ResponseEntity<Movimiento> createMovimiento(@RequestBody Movimiento movimiento) {
        Movimiento newMovimiento = movimientoService.createMovimiento(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovimiento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovimientoById(@PathVariable UUID id) {
        Optional<Movimiento> movimiento = movimientoService.getMovimientoById(id);
        return movimiento.map(ResponseEntity::ok)
                         .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> getAllMovimientos() {
        return ResponseEntity.ok(movimientoService.getAllMovimientos());
    }

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<List<Movimiento>> getMovimientosByCuenta(@PathVariable UUID idCuenta) {
        return ResponseEntity.ok(movimientoService.getMovimientosByCuentaId(idCuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> updateMovimiento(@PathVariable UUID id, @RequestBody Movimiento updatedMovimiento) {
        Movimiento movimientoUpdated = movimientoService.updateMovimiento(id, updatedMovimiento);
        return ResponseEntity.ok(movimientoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable UUID id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
