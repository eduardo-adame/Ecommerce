package com.ceas.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.ClienteEntity;
import com.ceas.proyecto.model.DetalleVentaEntity;
import com.ceas.proyecto.model.ProductoEntity;
import com.ceas.proyecto.model.VentaEntity;
import com.ceas.proyecto.repository.ClienteRepository;
import com.ceas.proyecto.repository.ProductoRepository;
import com.ceas.proyecto.repository.VentaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    //Procesar de venta
    @Transactional
    public VentaEntity procesarVenta(VentaEntity ventaRequest) {
        // Guardar la venta
        ventaRequest.setFecha(LocalDateTime.now());
        ventaRequest.setEstado("Pendiente");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ClienteEntity cliente = clienteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + username));
        ventaRequest.setCliente(cliente);

        double total = 0.0;
        if (ventaRequest.getDetalles() != null) {
            for (DetalleVentaEntity detalle : ventaRequest.getDetalles()) {
                ProductoEntity producto = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Producto no encontrado con ID: " + detalle.getProducto().getId()));

                if (producto.getStock() < detalle.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
                }

                producto.setStock(producto.getStock() - detalle.getCantidad());
                detalle.setPrecio(producto.getPrecio());
                detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
                detalle.setProducto(producto);
                detalle.setVenta(ventaRequest);
                total += detalle.getSubtotal();
            }
        }
        ventaRequest.setTotal(total);
        return ventaRepository.save(ventaRequest);
    }

    //Confirmar pago de una venta
    @Transactional
    public VentaEntity confirmarPago(Long idVenta) {
        VentaEntity venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con ID: " + idVenta));

        venta.setEstado("PAGADO");

        return ventaRepository.save(venta);
    }

    //Leer todas las ventas
    @Transactional(readOnly = true)
    public List<VentaEntity> obtenerTodos() {
        return ventaRepository.findAll();
    }

    //Leer ventas por cliente id
    @Transactional(readOnly = true)
    public List<VentaEntity> obtenerVentasPorClienteId(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }
    

    //Leer una venta por su id
    @Transactional(readOnly = true)
    public VentaEntity obtenerPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con id: " + id));
    }

    //Actualizar una venta
    @Transactional
    public VentaEntity actualizarVenta(Long id, VentaEntity detalleVenta) {
        VentaEntity ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con id: " + id));

        BeanUtils.copyProperties(detalleVenta, ventaExistente, "id");
        return ventaRepository.save(ventaExistente);
    }

    //Eliminar una venta por su id
    @Transactional
    public void eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        } else {
            ventaRepository.deleteById(id);
        }
    }
}
