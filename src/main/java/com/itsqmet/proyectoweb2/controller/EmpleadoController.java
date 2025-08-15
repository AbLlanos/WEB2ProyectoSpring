package com.itsqmet.proyectoweb2.controller;

import com.itsqmet.proyectoweb2.entity.Empleado;
import com.itsqmet.proyectoweb2.services.EmpleadoServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empleados")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    @Autowired
    private EmpleadoServices empleadoService;

    // Leer todos los empleados
    @GetMapping
    public List<Empleado> leer() {
        return empleadoService.mostrarEmpleados();
    }

    // Buscar empleado por ID
    @GetMapping("/{id}")
    public Optional<Empleado> buscarPorId(@PathVariable Long id) {
        return empleadoService.buscarEmpleadosPorId(id);
    }

    // Guardar empleado
    @PostMapping("/registroEmpleado")
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.guardarEmpleado(empleado);
    }

    // Eliminar empleado
    @DeleteMapping("/eliminar/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
    }

    // Actualizar empleado
    @PutMapping("/actualizar/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody Empleado empleado) {
        Optional<Empleado> empleadoOptional = empleadoService.buscarEmpleadosPorId(id);
        if (empleadoOptional.isPresent()) {
            Empleado empleadoExistente = empleadoOptional.get();
            empleadoExistente.setNombreCompleto(empleado.getNombreCompleto());
            empleadoExistente.setCorreoElectronico(empleado.getCorreoElectronico());
            empleadoExistente.setPassword(empleado.getPassword()); // Guardar tal cual
            empleadoExistente.setDireccion(empleado.getDireccion());
            empleadoExistente.setFechaNacimiento(empleado.getFechaNacimiento());
            empleadoExistente.setFechaRegistro(empleado.getFechaRegistro());
            empleadoExistente.setGenero(empleado.getGenero());
            empleadoExistente.setRol(empleado.getRol());
            empleadoExistente.setTelefono(empleado.getTelefono());
            return empleadoService.guardarEmpleado(empleadoExistente);
        }
        return null;
    }
}
