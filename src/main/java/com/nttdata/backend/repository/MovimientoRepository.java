package com.nttdata.backend.repository;

import com.nttdata.backend.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {
    List<Movimiento> findByIdcuenta_Id(UUID idCuenta);
}
