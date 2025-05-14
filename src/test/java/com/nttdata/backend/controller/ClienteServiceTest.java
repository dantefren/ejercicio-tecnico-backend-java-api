package com.nttdata.backend.controller;

import com.nttdata.backend.model.Cliente;
import com.nttdata.backend.model.Persona;
import com.nttdata.backend.repository.ClienteRepository;
import com.nttdata.backend.service.ClienteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testCrearCliente() {
        Persona persona = new Persona();
        persona.setId(UUID.randomUUID());
        persona.setNombre("Danilo Taco");

        Cliente cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setIdpersona(persona);
        cliente.setClienteid("CL123456");
        cliente.setContrasenia("123456789");
        cliente.setEstado(true);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.createCliente(cliente);

        assertNotNull(resultado);
        assertEquals("CL123456", resultado.getClienteid());
        assertEquals("123456789", resultado.getContrasenia());
        assertTrue(resultado.getEstado());
        verify(clienteRepository, times(1)).save(cliente);
    }
}
