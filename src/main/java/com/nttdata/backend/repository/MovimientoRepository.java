package com.nttdata.backend.repository;

import com.nttdata.backend.model.Cuenta;
import com.nttdata.backend.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {
    List<Movimiento> findByIdcuenta_Id(UUID idcuenta);
    List<Movimiento> findByidcuenta_IdAndFechaBetween(UUID idcuenta, Instant fechaInicio, Instant fechaFin);

}
