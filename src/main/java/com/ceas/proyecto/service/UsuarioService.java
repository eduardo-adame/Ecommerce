package com.ceas.proyecto.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ceas.proyecto.dto.RegistroRequest;
import com.ceas.proyecto.model.ClienteEntity;
import com.ceas.proyecto.model.Rol;
import com.ceas.proyecto.model.UsuarioEntity;
import com.ceas.proyecto.repository.ClienteRepository;
import com.ceas.proyecto.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public UsuarioEntity saveUsuario(RegistroRequest request) {
        
        if(usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());

        Rol rol = "ROLE_ADMIN".equals(request.getRol()) ? Rol.ROLE_ADMIN : Rol.ROLE_CLIENTE;
        usuario.setRol(rol);
        UsuarioEntity savedUsuario = usuarioRepository.save(usuario);

        if(rol == Rol.ROLE_CLIENTE) {
            
            ClienteEntity cliente = new ClienteEntity();
            cliente.setNombre(request.getNombre());
            cliente.setApellido(request.getApellido());
            cliente.setCorreo(request.getUsername());
            cliente.setUsername(request.getUsername());
            cliente.setTelefono(request.getTelefono());
            cliente.setDireccion(request.getDireccion());
            clienteRepository.save(cliente);
         
        }
        return savedUsuario;
    }
}

