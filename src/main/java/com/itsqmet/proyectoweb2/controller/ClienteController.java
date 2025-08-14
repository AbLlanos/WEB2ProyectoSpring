package com.itsqmet.proyectoweb2.controller;


import com.itsqmet.proyectoweb2.entity.Cliente;
import com.itsqmet.proyectoweb2.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Leer todos los clientes
    @GetMapping
    public List<Cliente> leer() {
        return clienteService.mostrarClientes();
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public Optional<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id);
    }

    // Guardar cliente
    @PostMapping("/guardar")
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    // Eliminar cliente
    @DeleteMapping("/eliminar/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }

    // Actualizar cliente
    @PutMapping("/actualizar/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteExistente = clienteOptional.get();

            clienteExistente.setGenero(cliente.getGenero());
            clienteExistente.setNombreCompleto(cliente.getNombreCompleto());
            clienteExistente.setRol(cliente.getRol());
            clienteExistente.setSuscripcionActiva(cliente.isSuscripcionActiva());
            clienteExistente.setFechaActivacion(cliente.getFechaActivacion());
            clienteExistente.setTipoSuscripcion(cliente.getTipoSuscripcion());
            clienteExistente.setTelefono(cliente.getTelefono());

            // Solo actualizar password si viene no vacío
            if (cliente.getPassword() != null && !cliente.getPassword().isEmpty()) {
                clienteExistente.setPassword(cliente.getPassword());
            }

            return clienteService.guardarCliente(clienteExistente);
        }
        return null;
    }

}
