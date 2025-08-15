package com.itsqmet.proyectoweb2.services;

import com.itsqmet.proyectoweb2.entity.Pedido;
import com.itsqmet.proyectoweb2.entity.PedidoProducto;
import com.itsqmet.proyectoweb2.entity.Producto;
import com.itsqmet.proyectoweb2.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidosService {

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService; // <-- inyectar ProductoService

    public PedidosService(PedidoRepository pedidoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
    }

    public Pedido guardarPedido(Pedido pedido) {
        pedido.setFecha(LocalDateTime.now());

        if (pedido.getProductos() != null) {
            pedido.getProductos().forEach(pp -> {
                pp.setPedido(pedido);

                // Buscar el producto real desde la base de datos
                Producto producto = productoService.buscarPorId(pp.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                // Reducir stock usando el valor real
                int nuevaCantidad = (producto.getCantidad() != null ? producto.getCantidad() : 0) - pp.getCantidad();
                producto.setCantidad(Math.max(0, nuevaCantidad));

                // Guardar producto actualizado
                productoService.guardarProducto(producto);

                // Asignar producto completo al PedidoProducto
                pp.setProducto(producto);
            });
        }

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
}
