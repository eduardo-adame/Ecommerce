package com.ceas.proyecto.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ceas.proyecto.model.ProductoEntity;
import com.ceas.proyecto.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;

    @Transactional(readOnly = true)
    public List<ProductoEntity> obtenerTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductoEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + id));
    }

    @Transactional
    public ProductoEntity guardarProducto(ProductoEntity producto) {
        return repository.save(producto);
        //aqui pueden ir todas las validaciones que se quieran hacer antes de guardar el producto
    }

    @Transactional
    public void eliminarProducto(Long id) {
        
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }else {
            repository.deleteById(id);
        }
    }

}