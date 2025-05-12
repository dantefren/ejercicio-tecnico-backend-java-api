package com.nttdata.backend.repository;

import com.nttdata.backend.model.Cuenta;
import com.nttdata.backend.model.Persona;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, UUID> {
    List<Cuenta> findByIdpersona(Persona idpersona);
}
