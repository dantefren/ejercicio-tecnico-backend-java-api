package com.nttdata.backend.service;

import com.nttdata.backend.repository.CuentaRepository;
import com.nttdata.backend.model.Cuenta;
import com.nttdata.backend.model.Persona;
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    //DTACO: C
    public Cuenta createCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    //DTACO: R
    public Optional<Cuenta> getCuentaById(UUID id) {
        return cuentaRepository.findById(id);
    }

    public List<Cuenta> findByPersona(Persona persona) {
        List<Cuenta> cuentas = cuentaRepository.findByIdpersona(persona);
        return cuentas;
    }
    
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    //DTACO: U
    @Transactional
    public Cuenta updateCuenta(UUID id, Cuenta updatedCuenta) {
        return cuentaRepository.findById(id).map(cuenta -> {
            cuenta.setNumerocuenta(updatedCuenta.getNumerocuenta());
            cuenta.setTipo(updatedCuenta.getTipo());
            cuenta.setSaldoinicial(updatedCuenta.getSaldoinicial());
            cuenta.setEstado(updatedCuenta.getEstado());
            return cuentaRepository.save(cuenta);
        }).orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    //DTACO: D
    @Transactional
    public void deleteCuenta(UUID id) {
        cuentaRepository.deleteById(id);
    }
}
