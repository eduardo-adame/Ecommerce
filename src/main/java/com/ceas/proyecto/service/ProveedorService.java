package com.ceas.proyecto.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.ProveedorEntity;
import com.ceas.proyecto.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository repository;

    //Leer todos los proveedores
    @Transactional(readOnly = true)
    public List<ProveedorEntity> obtenerTodos() {
        return repository.findAll();
    }

    //Leer un proveedor por su id
    @Transactional(readOnly = true)
    public ProveedorEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Proveedor no encontrado con id: " + id));
    }

    //Guardar un proveedor
    @Transactional
    public ProveedorEntity guardarProveedor(ProveedorEntity proveedor) {
        return repository.save(proveedor);
    }

    //Actualizar un proveedor
    @Transactional
    public ProveedorEntity actualizarProveedor(Long id, ProveedorEntity detalleProveedor) {
        ProveedorEntity proveedorExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Proveedor no encontrado con id: " + id));

        BeanUtils.copyProperties(detalleProveedor, proveedorExistente, "id");
        return repository.save(proveedorExistente);
    }

    //Eliminar un proveedor por su id
    @Transactional
    public void eliminarProveedor(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con id: " + id);
        } else {
            repository.deleteById(id);
        }
    }
}
