package com.nttdata.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cuenta", schema = "Datos", indexes = {
        @Index(name = "idx_numero_cuenta", columnList = "numerocuenta")
}, uniqueConstraints = {
        @UniqueConstraint(name = "cuenta_numerocuenta_key", columnNames = {"numerocuenta"})
})
public class Cuenta {
    @Id
    //@ColumnDefault("gen_random_uuid()") //DTACO: PREFERIBLE QUE LOS UUID SEAN GENERADOS DIRECTAMENTE EN LA BDD
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idpersona", nullable = false)
    private Persona idpersona;

    @Size(max = 50)
    @NotNull
    @Column(name = "numerocuenta", nullable = false, length = 50)
    private String numerocuenta;

    @Size(max = 20)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @NotNull
    @Column(name = "saldoinicial", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldoinicial;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

}