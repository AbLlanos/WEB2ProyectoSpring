package com.itsqmet.proyectoweb2.repository;

import com.itsqmet.proyectoweb2.entity.Empleado;
import com.itsqmet.proyectoweb2.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByCorreoElectronico(String correo);
}
