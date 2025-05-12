package com.nttdata.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import com.nttdata.backend.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByClienteid(String clienteid);
    List<Cliente> findByEstado(Boolean estado);
}
