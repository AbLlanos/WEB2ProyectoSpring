package com.itsqmet.proyectoweb2.controller;

import com.itsqmet.proyectoweb2.entity.Pedido;
import com.itsqmet.proyectoweb2.entity.Producto;
import com.itsqmet.proyectoweb2.services.PedidosService;
import com.itsqmet.proyectoweb2.services.ProductoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

    private final PedidosService pedidoService;
    private final ProductoService productoService; // inyectamos el servicio de productos

    public PedidoController(PedidosService pedidoService, ProductoService productoService) {
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }

    @PostMapping("/guardar")
    public Pedido guardarPedido(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        return pedidoService.listarPedidosPorCliente(clienteId);
    }

    // Nuevo endpoint para actualizar stock
    @PutMapping("/actualizarStock/{id}")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        int nuevaCantidad = body.get("cantidad") != null ? body.get("cantidad") : 0;

        Producto producto = productoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setCantidad(nuevaCantidad);
        productoService.guardarProducto(producto);

        return ResponseEntity.ok().build();
    }
}
