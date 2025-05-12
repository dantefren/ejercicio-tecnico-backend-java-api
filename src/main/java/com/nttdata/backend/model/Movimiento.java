package com.nttdata.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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
    //@ColumnDefault("gen_random_uuid()") //DTACO: PREFERIBLE QUE LOS UUID SEAN GENERADOS DIRECTAMENTE EN LA BDD
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
    private TipoMovimiento tipo; //DTACO: Se cambia el string de la base para usar enum como un catalogo de tipos de movimientos

    @NotNull
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(name = "saldo", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;

    //@ColumnDefault("CURRENT_TIMESTAMP") //DTACO: PREFERIBLE QUE LAS FECHAS SEAN MANEJADAS POR LA BDD CUANDO SON REGISTRADAS
    @Column(name = "fecha")
    private Instant fecha;

}