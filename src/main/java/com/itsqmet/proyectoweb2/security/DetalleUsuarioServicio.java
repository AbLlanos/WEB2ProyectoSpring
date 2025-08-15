package com.itsqmet.proyectoweb2.security;

import com.itsqmet.proyectoweb2.configuration.UsuarioPrincipal;
import com.itsqmet.proyectoweb2.entity.Usuario;
import com.itsqmet.proyectoweb2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class DetalleUsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new UsuarioPrincipal(usuario);
    }
}
