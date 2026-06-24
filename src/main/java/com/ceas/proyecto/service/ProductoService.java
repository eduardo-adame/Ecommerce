package com.ceas.proyecto.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.ProductoEntity;
import com.ceas.proyecto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;

    //Leer todos los productos
    @Transactional(readOnly = true)
    public List<ProductoEntity> obtenerTodos() {
        return repository.findAll();
    }

    //Obtener un producto por su id
    @Transactional(readOnly = true)
    public ProductoEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + id));
    }

    //Guardar un producto
    @Transactional
    public ProductoEntity guardarProducto(ProductoEntity producto) {
        return repository.save(producto);
        //aqui pueden ir todas las validaciones que se quieran hacer antes de guardar el producto
    }

    //Actualizar un producto
    @Transactional
    public ProductoEntity actualizarProducto(Long id, ProductoEntity detallePrtoducto) {
        ProductoEntity productoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + id));

        BeanUtils.copyProperties(detallePrtoducto, productoExistente, "id");
        return repository.save(productoExistente);
    }

    //Eliminar un producto por su id
    @Transactional
    public void eliminarProducto(Long id) {
        
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }else {
            repository.deleteById(id);
        }
    }
}