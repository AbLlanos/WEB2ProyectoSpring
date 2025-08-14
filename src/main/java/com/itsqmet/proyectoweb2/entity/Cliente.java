package com.itsqmet.proyectoweb2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Data
public class Cliente extends Usuario {

    @NotBlank(message = "La cédula es obligatoria")
    @Column(nullable = false, unique = true, length = 20)
    private String cedula;

    @Column(length = 255)
    private String direccion;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 20)
    private String genero;

    @Column(name = "suscripcion_activa")
    private boolean suscripcionActiva;

    @Column(name = "fecha_activacion")
    private LocalDateTime fechaActivacion;

    @Column(name = "tipo_suscripcion", length = 50)
    private String tipoSuscripcion;

    @Column(length = 20)
    private String telefono;
}
