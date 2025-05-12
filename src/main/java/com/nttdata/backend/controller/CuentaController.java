package com.nttdata.backend.controller;

import com.nttdata.backend.service.CuentaService;
import com.nttdata.backend.model.Cuenta;
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) {
        Cuenta newCuenta = cuentaService.createCuenta(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCuenta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCuentaById(@PathVariable UUID id) {
        Optional<Cuenta> cuenta = cuentaService.getCuentaById(id);
        return cuenta.map(ResponseEntity::ok)
                     .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        return ResponseEntity.ok(cuentaService.getAllCuentas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable UUID id, @RequestBody Cuenta updatedCuenta) {
        Cuenta cuentaUpdated = cuentaService.updateCuenta(id, updatedCuenta);
        return ResponseEntity.ok(cuentaUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable UUID id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
