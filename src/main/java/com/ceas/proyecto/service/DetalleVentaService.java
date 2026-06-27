package com.ceas.proyecto.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.DetalleVentaEntity;
import com.ceas.proyecto.repository.DetalleVentaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {

    private final DetalleVentaRepository repository;

    //Leer todos los detalles de venta
    @Transactional(readOnly = true)
    public List<DetalleVentaEntity> obtenerTodos() {
        return repository.findAll();
    }

    //Leer un detalle de venta por su id
    @Transactional(readOnly = true)
    public DetalleVentaEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Detalle de venta no encontrado con id: " + id));
    }

    //Guardar un detalle de venta
    @Transactional
    public DetalleVentaEntity guardarDetalleVenta(DetalleVentaEntity detalleVenta) {
        return repository.save(detalleVenta);
    }

    //Actualizar un detalle de venta
    @Transactional
    public DetalleVentaEntity actualizarDetalleVenta(Long id, DetalleVentaEntity detalleDetalleVenta) {
        DetalleVentaEntity detalleExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Detalle de venta no encontrado con id: " + id));

        BeanUtils.copyProperties(detalleDetalleVenta, detalleExistente, "id");
        return repository.save(detalleExistente);
    }

    //Eliminar un detalle de venta por su id
    @Transactional
    public void eliminarDetalleVenta(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Detalle de venta no encontrado con id: " + id);
        } else {
            repository.deleteById(id);
        }
    }
}
