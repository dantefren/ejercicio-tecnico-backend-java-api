package com.nttdata.backend.controller;

import com.nttdata.backend.service.ClienteService;
import com.nttdata.backend.model.Cliente;
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
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> createCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteService.createCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(newCliente, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> getClienteById(@PathVariable UUID id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        return cliente.map(c -> ResponseEntity.ok(new ApiResponse<>(c, null)))
                      .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> getAllClientes() {
        return ResponseEntity.ok(new ApiResponse<>(clienteService.getAllClientes(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCliente(@PathVariable UUID id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok(new ApiResponse<>(null, null));
    }
}
