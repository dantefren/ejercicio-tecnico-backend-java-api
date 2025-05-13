package com.nttdata.backend.controller;

import com.nttdata.backend.service.PersonaService;
import com.nttdata.backend.model.Persona;
import com.nttdata.backend.model.PersonaCuenta;
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
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Persona>> createPersona(@RequestBody Persona persona) {
        Persona newPersona = personaService.createPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(newPersona, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Persona>> getPersonaById(@PathVariable UUID id) {
        Optional<Persona> persona = personaService.getPersonaById(id);
        return persona.map(p -> ResponseEntity.ok(new ApiResponse<>(p, null)))
                      .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<ApiResponse<PersonaCuenta>> getPersonaCuentaById(@PathVariable UUID id) {
        Optional<PersonaCuenta> personaCuenta = personaService.getPersonaCuentaById(id);
        return personaCuenta.map(pc -> ResponseEntity.ok(new ApiResponse<>(pc, null)))
                            .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Persona>>> getAllPersonas() {
        return ResponseEntity.ok(new ApiResponse<>(personaService.getAllPersonas(), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Persona>> updatePersona(@PathVariable UUID id, @RequestBody Persona updatedPersona) {
        Persona personaUpdated = personaService.updatePersona(id, updatedPersona);
        return ResponseEntity.ok(new ApiResponse<>(personaUpdated, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePersona(@PathVariable UUID id) {
        personaService.deletePersona(id);
        return ResponseEntity.ok(new ApiResponse<>(null, null)); 
    }
}
