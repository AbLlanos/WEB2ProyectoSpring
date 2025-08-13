package com.itsqmet.proyectoweb2.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
    private String categoria;

    @NotBlank(message = "El campo disponible es obligatorio")
    private String disponible;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El IVA es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El IVA no puede ser negativo")
    @DecimalMax(value = "1.00", inclusive = true, message = "El IVA debe estar en formato decimal (Ej: 0.15 para 15%)")
    private Double iva;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
    private String img;

    @NotBlank(message = "Los ingredientes son obligatorios")
    @Size(max = 1000, message = "Los ingredientes no pueden superar los 1000 caracteres")
    private String ingredientes;
}
