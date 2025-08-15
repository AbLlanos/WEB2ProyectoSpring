package com.itsqmet.proyectoweb2.services;

import com.itsqmet.proyectoweb2.entity.Empleado;
import com.itsqmet.proyectoweb2.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServices {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Inyector del encriptador de contraseñas
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Obtener todos los empleados
    public List<Empleado> mostrarEmpleados() {
        return empleadoRepository.findAll();
    }

    // Buscar empleado por ID
    public Optional<Empleado> buscarEmpleadosPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    // Guardar empleado con contraseña encriptada
    public Empleado guardarEmpleado(Empleado empleado) {
        String passwordEncriptada = passwordEncoder.encode(empleado.getPassword());
        empleado.setPassword(passwordEncriptada);
        return empleadoRepository.save(empleado);
    }

    // Actualizar empleado (encripta solo si cambia la contraseña)
    public Empleado actualizarEmpleado(Long id, Empleado empleado) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findById(id);
        if (empleadoExistente.isPresent()) {
            if (!empleado.getPassword().equals(empleadoExistente.get().getPassword())) {
                String passwordEncriptada = passwordEncoder.encode(empleado.getPassword());
                empleado.setPassword(passwordEncriptada);
            }
            empleado.setId(id);
            return empleadoRepository.save(empleado);
        }
        return null;
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
