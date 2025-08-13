package com.itsqmet.proyectoweb2.repository;

import com.itsqmet.proyectoweb2.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<Producto, Long> {
}
