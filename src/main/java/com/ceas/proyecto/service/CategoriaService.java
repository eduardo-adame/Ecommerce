package com.ceas.proyecto.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.CategoriaEntity;
import com.ceas.proyecto.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    //Leer todas las categorias
    @Transactional(readOnly = true)
    public List<CategoriaEntity> obtenerTodos() {
        return repository.findAll();
    }

    //Leer una categoria por su id
    @Transactional(readOnly = true)
    public CategoriaEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Categoria no encontrada con id: " + id));
    }

    //Guardar una categoria
    @Transactional
    public CategoriaEntity guardarCategoria(CategoriaEntity categoria) {
        return repository.save(categoria);
    }

    //Actualizar una categoria
    @Transactional
    public CategoriaEntity actualizarCategoria(Long id, CategoriaEntity detalleCategoria) {
        CategoriaEntity categoriaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Categoria no encontrada con id: " + id));

        BeanUtils.copyProperties(detalleCategoria, categoriaExistente, "id");
        return repository.save(categoriaExistente);
    }

    //Eliminar una categoria por su id
    @Transactional
    public void eliminarCategoria(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada con id: " + id);
        } else {
            repository.deleteById(id);
        }
    }
}
