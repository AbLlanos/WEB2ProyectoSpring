package com.itsqmet.proyectoweb2.services;

import com.itsqmet.proyectoweb2.entity.Cliente;
import com.itsqmet.proyectoweb2.entity.Usuario;
import com.itsqmet.proyectoweb2.repository.ClienteRepository;
import com.itsqmet.proyectoweb2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cliente guardarCliente(Cliente cliente) {
        // Guardar en texto plano
        return clienteRepository.save(cliente);
    }


    public List<Cliente> mostrarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Usuario> buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreoElectronico(correo);
    }

    public List<Usuario> buscarUsuariosPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

}
