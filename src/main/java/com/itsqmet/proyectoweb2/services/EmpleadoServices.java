package com.itsqmet.proyectoweb2.services;

import com.itsqmet.proyectoweb2.entity.Empleado;
import com.itsqmet.proyectoweb2.entity.Producto;
import com.itsqmet.proyectoweb2.repository.EmpleadoRepository;
import com.itsqmet.proyectoweb2.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmpleadoServices {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Obtener todos los empleados
    public List<Empleado> mostrarEmpleados() {
        return empleadoRepository.findAll();
    }

    // Buscar empleado por ID
    public Optional<Empleado> buscarEmpleadosPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    // Guardar empleado
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // Actualizar empleado
    public Empleado actualizarEmpleado(Long id, Empleado empleado) {
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    // Eliminar empleado
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    // Buscar por correo electrónico
    public List<Empleado> buscarPorCorreo(String correo) {
        return empleadoRepository.findByCorreoElectronico(correo);
    }
}
