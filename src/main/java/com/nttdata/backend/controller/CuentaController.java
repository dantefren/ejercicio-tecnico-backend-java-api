package com.nttdata.backend.controller;

import com.nttdata.backend.service.CuentaService;
import com.nttdata.backend.model.Cuenta;
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
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cuenta>> createCuenta(@RequestBody Cuenta cuenta) {
        Cuenta newCuenta = cuentaService.createCuenta(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(newCuenta, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cuenta>> getCuentaById(@PathVariable UUID id) {
        Optional<Cuenta> cuenta = cuentaService.getCuentaById(id);
        return cuenta.map(c -> ResponseEntity.ok(new ApiResponse<>(c, null)))
                     .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cuenta>>> getAllCuentas() {
        return ResponseEntity.ok(new ApiResponse<>(cuentaService.getAllCuentas(), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cuenta>> updateCuenta(@PathVariable UUID id, @RequestBody Cuenta updatedCuenta) {
        Cuenta cuentaUpdated = cuentaService.updateCuenta(id, updatedCuenta);
        return ResponseEntity.ok(new ApiResponse<>(cuentaUpdated, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCuenta(@PathVariable UUID id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.ok(new ApiResponse<>(null, null));
    }
}
