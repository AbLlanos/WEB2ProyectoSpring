package com.itsqmet.proyectoweb2.controller;


import com.itsqmet.proyectoweb2.entity.Producto;
import com.itsqmet.proyectoweb2.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/productos")

@CrossOrigin(origins = "http://localhost:4200")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Leer todos los productos
    @GetMapping
    public List<Producto> leer() {

        return productoService.mostrarProductos();

    }

    // Buscar producto por ID
    @GetMapping("/{id}")

    public Optional<Producto> buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    // Guardar producto con validación
    @PostMapping("/guardarProducto")

    public Producto guardarProducto(@Valid @RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    // Eliminar producto
    @DeleteMapping("/eliminar/{id}")

    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    // Actualizar producto con validación
    @PutMapping("/actualizar/{id}")

    public Producto actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Optional<Producto> productoOptional = productoService.buscarPorId(id);
        if (productoOptional.isPresent()) {
            Producto productoExistente = productoOptional.get();
            // Actualizar todos los campos
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setCategoria(producto.getCategoria());
            productoExistente.setDisponible(producto.getDisponible());
            productoExistente.setCantidad(producto.getCantidad());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setIva(producto.getIva());
            productoExistente.setImg(producto.getImg());
            productoExistente.setIngredientes(producto.getIngredientes());

            return productoService.guardarProducto(productoExistente);
        }

        return null;

    }

}
