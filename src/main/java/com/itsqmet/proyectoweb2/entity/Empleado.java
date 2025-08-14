package com.itsqmet.proyectoweb2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombreCompleto;

    @NotBlank
    @Email
    private String correoElectronico;

    @NotBlank
    private String password;

    @NotBlank
    private String rol;

    @NotBlank
    @Column(unique = true)
    private String cedula;

    private String direccion;
    private LocalDate fechaNacimiento;
    private String genero;
    private String telefono;

    @PastOrPresent
    private LocalDate fechaRegistro;
}
