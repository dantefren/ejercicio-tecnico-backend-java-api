package com.nttdata.backend.service;
import com.nttdata.backend.repository.CuentaRepository;
import com.nttdata.backend.repository.PersonaRepository;
import com.nttdata.backend.model.Cuenta;
import com.nttdata.backend.model.Persona;
import com.nttdata.backend.model.PersonaCuenta;
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonaService {

    private final CuentaRepository cuentaRepository;
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository, CuentaRepository cuentaRepository) {
        this.personaRepository = personaRepository;
        this.cuentaRepository = cuentaRepository;
    }

    //DTACO: C
    public Persona createPersona(Persona persona) {
        persona.setId(UUID.randomUUID());
        return personaRepository.save(persona);
    }

    //DTACO: R
    public Optional<Persona> getPersonaById(UUID id) {
        return personaRepository.findById(id);
    }

    public Optional<PersonaCuenta> getPersonaCuentaById(UUID id) {
        PersonaCuenta respuesta = new PersonaCuenta();
    
        // Buscar la persona
        respuesta.persona = personaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));

        // Buscar las cuentas asociadas a la persona
        List<Cuenta> cuentas = cuentaRepository.findByIdpersona(respuesta.persona);
        
        // Validar si la lista de cuentas está vacía
        if (cuentas.isEmpty()) {
            throw new ServiceException(Error.RECURSO_NO_ENCONTRADO);
        }
        
        respuesta.cuentas = cuentas;

        return Optional.of(respuesta);
    }


    public Optional<Persona> getPersonaByIdentificacion(String identificacion) {
        return personaRepository.findByIdentificacion(identificacion);
    }

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    //DTACO: U
    @Transactional
    public Persona updatePersona(UUID id, Persona updatedPersona) {
        return personaRepository.findById(id).map(persona -> {
            persona.setNombre(updatedPersona.getNombre());
            persona.setGenero(updatedPersona.getGenero());
            persona.setEdad(updatedPersona.getEdad());
            persona.setIdentificacion(updatedPersona.getIdentificacion());
            persona.setDireccion(updatedPersona.getDireccion());
            persona.setTelefono(updatedPersona.getTelefono());
            return personaRepository.save(persona);
        }).orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    //DTACO: D
    @Transactional
    public void deletePersona(UUID id) {
        personaRepository.deleteById(id);
    }
}
