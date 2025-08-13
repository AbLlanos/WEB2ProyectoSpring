package com.itsqmet.proyectoweb2.services;

import com.itsqmet.proyectoweb2.entity.Producto;
import com.itsqmet.proyectoweb2.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductosRepository productosRepository;

    // Obtener todos los productos
    public List<Producto> mostrarProductos() {
        return productosRepository.findAll();
    }

    // Buscar producto por ID
    public Optional<Producto> buscarPorId(Long id) {
        return productosRepository.findById(id);
    }

    // Guardar producto y devolver el objeto guardado
    public Producto guardarProducto(Producto producto) {
        return productosRepository.save(producto);
    }

    // Eliminar producto por ID
    public void eliminarProducto(Long id) {
        productosRepository.deleteById(id);
    }

}
