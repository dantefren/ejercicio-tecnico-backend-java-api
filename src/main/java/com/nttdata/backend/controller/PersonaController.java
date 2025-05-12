package com.nttdata.backend.controller;

import com.nttdata.backend.service.PersonaService;
import com.nttdata.backend.model.Persona;
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;
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
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona newPersona = personaService.createPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPersona);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonaById(@PathVariable UUID id) {
        Optional<Persona> persona = personaService.getPersonaById(id);
        return persona.map(ResponseEntity::ok)
                      .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return ResponseEntity.ok(personaService.getAllPersonas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable UUID id, @RequestBody Persona updatedPersona) {
        Persona personaUpdated = personaService.updatePersona(id, updatedPersona);
        return ResponseEntity.ok(personaUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable UUID id) {
        personaService.deletePersona(id);
        return ResponseEntity.noContent().build();
    }
}
