package com.nttdata.backend.controller;

import com.nttdata.backend.service.PersonaService;
import com.nttdata.backend.model.Persona;
import com.nttdata.backend.common.ApiResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.nttdata.backend.exception.ServiceException;

class PersonaControllerTest {

    @Mock
    private PersonaService personaService;

    @InjectMocks
    private PersonaController personaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPersonaById_Existe() {
        UUID id = UUID.randomUUID();
        Persona persona = new Persona();
        persona.setId(id);
        persona.setNombre("Juan Pérez");
        persona.setEdad(34);

        when(personaService.getPersonaById(id)).thenReturn(Optional.of(persona));

        ResponseEntity<ApiResponse<Persona>> response = personaController.getPersonaById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
        assertEquals("000", response.getBody().getMessage().getCode());
    }

    @Test
    void testGetPersonaById_NoExiste() {
        UUID id = UUID.randomUUID();

        when(personaService.getPersonaById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            personaController.getPersonaById(id);
        });

        assertTrue(exception instanceof ServiceException);
    }
}
