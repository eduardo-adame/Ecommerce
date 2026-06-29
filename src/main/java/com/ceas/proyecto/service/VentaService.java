package com.ceas.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.DetalleVentaEntity;
import com.ceas.proyecto.model.ProductoEntity;
import com.ceas.proyecto.model.VentaEntity;
import com.ceas.proyecto.repository.ProductoRepository;
import com.ceas.proyecto.repository.VentaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    //Procesar de venta
    @Transactional
    public VentaEntity procesarVenta(VentaEntity ventaRequest) {
        // Guardar la venta
        ventaRequest.setFecha(LocalDateTime.now());
        ventaRequest.setEstado("Pendiente");

        double total = 0.0;
        for (DetalleVentaEntity detalle : ventaRequest.getDetalles()) {
            //Actualizar el stock del producto y calcular el subtotal
            ProductoEntity producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow();
                    producto.setStock(producto.getStock() - detalle.getCantidad());
        detalle.setPrecio(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio()*detalle.getCantidad());
        detalle.setVenta(ventaRequest);
        total += detalle.getSubtotal();
        }
        ventaRequest.setTotal(total);
        return ventaRepository.save(ventaRequest);
    }

    //Leer todas las ventas
    @Transactional(readOnly = true)
    public List<VentaEntity> obtenerTodos() {
        return ventaRepository.findAll();
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
