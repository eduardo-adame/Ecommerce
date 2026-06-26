package com.ceas.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceas.proyecto.model.ProductoEntity;
import com.ceas.proyecto.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping("/api/v1/productos") //Mapeo de la ruta para el controlador
@CrossOrigin(origins = "http://localhost:3000") //Permiso a React para consumir la API
@RequiredArgsConstructor //Inyección de dependencias
public class ProductoController {
    private final ProductoService productoService;

    //Endpoint para listar todos los productos
    @GetMapping("/")
    public ResponseEntity<List<ProductoEntity>> listar() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    //Endpoint para obtener un producto por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para guardar un producto
    @PostMapping("/")
    public ResponseEntity<ProductoEntity> guardar(@RequestBody ProductoEntity producto) {
        ProductoEntity nuevo = productoService.guardarProducto(producto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //Endpoint para actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoEntity producto) {
        try{
            ProductoEntity productoAct = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoAct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para eliminar un producto por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoEntity> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
