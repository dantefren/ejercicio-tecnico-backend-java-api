package com.nttdata.backend.repository;

import com.nttdata.backend.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {
    Optional<Persona> findById(UUID id);
    Optional<Persona> findByIdentificacion(String identificacion);
}
