package com.itsqmet.proyectoweb2.repository;

import com.itsqmet.proyectoweb2.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Optional<Cliente> findByCedula(String cedula);

    Optional<Cliente> findByCorreoElectronico(String correoElectronico);

    List<Cliente> findByRol(String rol);


}
