package com.ceas.proyecto.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceas.proyecto.model.CategoriaEntity;
import com.ceas.proyecto.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    //Endpoint para listar todas las categorias
    @GetMapping("/")
    public ResponseEntity<List<CategoriaEntity>> listar() {
        return ResponseEntity.ok(categoriaService.obtenerTodos());
    }

    //Endpoint para obtener una categoria por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoriaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para guardar una categoria
    @PostMapping("/")
    public ResponseEntity<CategoriaEntity> guardar(@RequestBody CategoriaEntity categoria) {
        CategoriaEntity nuevo = categoriaService.guardarCategoria(categoria);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //Endpoint para actualizar una categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody CategoriaEntity categoria) {
        try {
            CategoriaEntity categoriaAct = categoriaService.actualizarCategoria(id, categoria);
            return ResponseEntity.ok(categoriaAct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para eliminar una categoria por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoriaEntity> eliminar(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
