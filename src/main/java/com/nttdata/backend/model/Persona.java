package com.nttdata.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "persona", schema = "Datos", indexes = {
        @Index(name = "idx_identificacion", columnList = "identificacion")
}, uniqueConstraints = {
        @UniqueConstraint(name = "persona_identificacion_key", columnNames = {"identificacion"})
})
public class Persona {
    @Id
    //@ColumnDefault("gen_random_uuid()") //DTACO: PREFERIBLE QUE LOS UUID SEAN GENERADOS DIRECTAMENTE EN LA BDD
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 20)
    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "edad")
    private Integer edad;

    @Size(max = 50)
    @NotNull
    @Pattern(regexp = "\\d{10,50}") // DTACO: Ajusta el patrón según el tipo de identificación
    @Column(name = "identificacion", nullable = false, length = 50)
    private String identificacion;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Size(max = 20)
    @Pattern(regexp = "\\+?\\d{7,20}") // DTACO: Permite números internacionales con "+"
    @Column(name = "telefono", length = 20)
    private String telefono;

}