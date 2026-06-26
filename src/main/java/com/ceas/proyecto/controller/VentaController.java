package com.ceas.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceas.proyecto.model.VentaEntity;
import com.ceas.proyecto.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/ventas")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;

    //Endpoint para listar todas las ventas
    @GetMapping("/")
    public ResponseEntity<List<VentaEntity>> listar() {
        return ResponseEntity.ok(ventaService.obtenerTodos());
    }

    //Endpoint para obtener una venta por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ventaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para guardar una venta
    @PostMapping("/")
    public ResponseEntity<VentaEntity> guardar(@RequestBody VentaEntity venta) {
        VentaEntity nuevo = ventaService.guardarVenta(venta);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //Endpoint para actualizar una venta
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody VentaEntity venta) {
        try {
            VentaEntity ventaAct = ventaService.actualizarVenta(id, venta);
            return ResponseEntity.ok(ventaAct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para eliminar una venta por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<VentaEntity> eliminar(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
