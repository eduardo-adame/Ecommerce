package com.ceas.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceas.proyecto.model.ClienteEntity;
import com.ceas.proyecto.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    //Endpoint para listar todos los clientes
    @GetMapping("/")
    public ResponseEntity<List<ClienteEntity>> listar() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    //Endpoint para obtener un cliente por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para guardar un cliente
    @PostMapping("/")
    public ResponseEntity<ClienteEntity> guardar(@RequestBody ClienteEntity cliente) {
        ClienteEntity nuevo = clienteService.guardarCliente(cliente);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //Endpoint para actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ClienteEntity cliente) {
        try {
            ClienteEntity clienteAct = clienteService.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteAct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Endpoint para eliminar un cliente por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteEntity> eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
