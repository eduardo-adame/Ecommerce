package com.ceas.proyecto.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceas.proyecto.model.ProveedorEntity;
import com.ceas.proyecto.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/proveedores")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorService proveedorService;

    //Endpoint para listar todos los proveedores
    @GetMapping("/")
    public ResponseEntity<List<ProveedorEntity>> listar() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    //Endpoint para obtener un proveedor por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(proveedorService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para guardar un proveedor
    @PostMapping("/")
    public ResponseEntity<ProveedorEntity> guardar(@RequestBody ProveedorEntity proveedor) {
        ProveedorEntity nuevo = proveedorService.guardarProveedor(proveedor);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //Endpoint para actualizar un proveedor
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProveedorEntity proveedor) {
        try {
            ProveedorEntity proveedorAct = proveedorService.actualizarProveedor(id, proveedor);
            return ResponseEntity.ok(proveedorAct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para eliminar un proveedor por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<ProveedorEntity> eliminar(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
