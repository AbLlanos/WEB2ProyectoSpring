package com.itsqmet.proyectoweb2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
public class Empleado extends Usuario {

    @NotBlank
    @Column(unique = true)
    private String cedula;

    private String direccion;

    private LocalDate fechaNacimiento;

    private String genero;

    private String telefono;

    private LocalDate fechaRegistro;
}
