package com.ceas.proyecto.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.VentaEntity;
import com.ceas.proyecto.repository.VentaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository repository;

    //Leer todas las ventas
    @Transactional(readOnly = true)
    public List<VentaEntity> obtenerTodos() {
        return repository.findAll();
    }

    //Leer una venta por su id
    @Transactional(readOnly = true)
    public VentaEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con id: " + id));
    }

    //Guardar una venta
    @Transactional
    public VentaEntity guardarVenta(VentaEntity venta) {
        return repository.save(venta);
    }

    //Actualizar una venta
    @Transactional
    public VentaEntity actualizarVenta(Long id, VentaEntity detalleVenta) {
        VentaEntity ventaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con id: " + id));

        BeanUtils.copyProperties(detalleVenta, ventaExistente, "id");
        return repository.save(ventaExistente);
    }

    //Eliminar una venta por su id
    @Transactional
    public void eliminarVenta(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        } else {
            repository.deleteById(id);
        }
    }
}
