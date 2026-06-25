package com.ceas.proyecto.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceas.proyecto.model.ClienteEntity;
import com.ceas.proyecto.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    //Leer todos los clientes
    @Transactional(readOnly = true)
    public List<ClienteEntity> obtenerTodos() {
        return repository.findAll();
    }

    //Leer un cliente por su id
    @Transactional(readOnly = true)
    public ClienteEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado con id: " + id));
    }

    //Guardar un cliente
    @Transactional
    public ClienteEntity guardarCliente(ClienteEntity cliente) {
        return repository.save(cliente);
    }

    //Actualizar un cliente
    @Transactional
    public ClienteEntity actualizarCliente(Long id, ClienteEntity detalleCliente) {
        ClienteEntity clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado con id: " + id));

        BeanUtils.copyProperties(detalleCliente, clienteExistente, "id");
        return repository.save(clienteExistente);
    }

    //Eliminar un cliente por su id
    @Transactional
    public void eliminarCliente(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        } else {
            repository.deleteById(id);
        }
    }
}
