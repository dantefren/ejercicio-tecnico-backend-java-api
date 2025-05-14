package com.nttdata.backend.service;

import com.nttdata.backend.repository.MovimientoRepository;
import com.nttdata.backend.model.Cuenta;
import com.nttdata.backend.model.Movimiento;
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;
import com.nttdata.backend.common.Constant.TipoMovimiento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final CuentaService cuentaService;

    public MovimientoService(MovimientoRepository movimientoRepository, CuentaService cuentaService) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaService = cuentaService;
    }

    //DTACO: C
    public Movimiento createMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    //DTACO: R
    public Optional<Movimiento> getMovimientoById(UUID id) {
        return movimientoRepository.findById(id);
    }

    public List<Movimiento> getMovimientosByCuentaId(UUID idCuenta) {
        return movimientoRepository.findByIdcuenta_Id(idCuenta);
    }

    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    //DTACO: U
    @Transactional
    public Movimiento updateMovimiento(UUID id, Movimiento updatedMovimiento) {
        return movimientoRepository.findById(id).map(movimiento -> {
            movimiento.setTipo(updatedMovimiento.getTipo());
            movimiento.setValor(updatedMovimiento.getValor());
            movimiento.setSaldo(updatedMovimiento.getSaldo());
            movimiento.setFecha(updatedMovimiento.getFecha());
            return movimientoRepository.save(movimiento);
        }).orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

        
    @Transactional
    public Movimiento registrarMovimiento(UUID idcuenta, BigDecimal monto) {
        Cuenta cuenta = cuentaService.getCuentaById(idcuenta)
            .orElseThrow(() -> new ServiceException(Error.RECURSO_NO_ENCONTRADO));

        //DTACO: Validar que haya saldo disponible antes de procesar el retiro
        if (monto.compareTo(BigDecimal.ZERO) < 0 && cuenta.getSaldo().compareTo(monto.abs()) < 0) {
            throw new ServiceException(Error.SALDO_NO_DISPONIBLE);
        }

        BigDecimal saldoAnterior = cuenta.getSaldo();
        cuenta.actualizarSaldo(monto);
        cuenta = cuentaService.actualizarCuenta(cuenta);

        Movimiento movimiento = new Movimiento(cuenta, 
            monto.compareTo(BigDecimal.ZERO) > 0 ? TipoMovimiento.DEPOSITO : TipoMovimiento.RETIRO,
            monto, saldoAnterior);

        movimiento.setId(UUID.randomUUID());
        movimiento.setFecha(Instant.now());

        return movimientoRepository.save(movimiento);
    }


    //DTACO: D
    @Transactional
    public void deleteMovimiento(UUID id) {
        movimientoRepository.deleteById(id);
    }
    
}
