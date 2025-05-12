package com.nttdata.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cliente", schema = "Datos", indexes = {
        @Index(name = "idx_clienteid", columnList = "clienteid"),
        @Index(name = "idx_cliente_estado", columnList = "estado")
}, uniqueConstraints = {
        @UniqueConstraint(name = "cliente_idpersona_key", columnNames = {"idpersona"}),
        @UniqueConstraint(name = "cliente_clienteid_key", columnNames = {"clienteid"})
})
public class Cliente {
    @Id
    //@ColumnDefault("gen_random_uuid()") //DTACO: PREFERIBLE QUE LOS UUID SEAN GENERADOS DIRECTAMENTE EN LA BDD.
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idpersona", nullable = false)
    private Persona idpersona;

    @Size(max = 50)
    @NotNull
    @Column(name = "clienteid", nullable = false, length = 50, unique = true) //DTACO: CLienteId debe ser único.
    private String clienteid;

    @Size(max = 255)
    @NotNull
    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

}