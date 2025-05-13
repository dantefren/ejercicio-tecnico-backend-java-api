package com.nttdata.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nttdata.backend.common.Constant.TipoMovimiento;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "movimiento", schema = "Datos", indexes = {
        @Index(name = "idx_fecha_movimiento", columnList = "fecha")
})
public class Movimiento {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idcuenta", nullable = false)
    private Cuenta idcuenta;

    @Size(max = 20)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoMovimiento tipo;

    @NotNull
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(name = "saldo", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;

    @Column(name = "fecha")
    private Instant fecha;

    //DTACO: Campos adicionales para el movimiento (no afectan la BD)
    @Transient
    private BigDecimal saldoAnterior;

    @Transient
    private BigDecimal saldoActual;

    public Movimiento() {
        this.id = UUID.randomUUID();
        this.fecha = Instant.now();
    }

    public Movimiento(Cuenta cuenta, TipoMovimiento tipo, BigDecimal valor, BigDecimal saldoAnterior) {
        this.id = UUID.randomUUID();
        this.idcuenta = cuenta;
        this.tipo = tipo;
        this.valor = valor != null ? valor : BigDecimal.ZERO;
        this.saldoAnterior = saldoAnterior != null ? saldoAnterior : BigDecimal.ZERO;
        this.saldoActual = this.saldoAnterior.add(this.valor);
        this.saldo = this.saldoActual;
        this.fecha = Instant.now();
    }
}
