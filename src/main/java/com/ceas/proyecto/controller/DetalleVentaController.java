package com.ceas.proyecto.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceas.proyecto.model.DetalleVentaEntity;
import com.ceas.proyecto.service.DetalleVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/detalle-ventas")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DetalleVentaController {
    private final DetalleVentaService detalleVentaService;

    //Endpoint para listar todos los detalles de venta
    @GetMapping("/")
    public ResponseEntity<List<DetalleVentaEntity>> listar() {
        return ResponseEntity.ok(detalleVentaService.obtenerTodos());
    }

    //Endpoint para obtener un detalle de venta por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(detalleVentaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para guardar un detalle de venta
    @PostMapping("/")
    public ResponseEntity<DetalleVentaEntity> guardar(@RequestBody DetalleVentaEntity detalleVenta) {
        DetalleVentaEntity nuevo = detalleVentaService.guardarDetalleVenta(detalleVenta);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //Endpoint para actualizar un detalle de venta
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody DetalleVentaEntity detalleVenta) {
        try {
            DetalleVentaEntity detalleAct = detalleVentaService.actualizarDetalleVenta(id, detalleVenta);
            return ResponseEntity.ok(detalleAct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para eliminar un detalle de venta por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<DetalleVentaEntity> eliminar(@PathVariable Long id) {
        detalleVentaService.eliminarDetalleVenta(id);
        return ResponseEntity.noContent().build();
    }
}
