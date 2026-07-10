package com.ceas.proyecto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceas.proyecto.dto.AuthRequest;
import com.ceas.proyecto.dto.AuthResponse;
import com.ceas.proyecto.dto.RegistroRequest;
import com.ceas.proyecto.model.UsuarioEntity;
import com.ceas.proyecto.repository.UsuarioRepository;
import com.ceas.proyecto.security.JwtTokenProvider;
import com.ceas.proyecto.service.UsuarioService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), 
                    request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            String username = authentication.getName();
            String nombre = usuarioRepository.findByUsername(username)
                    .map(UsuarioEntity::getNombre)
                    .orElse(username);

            return ResponseEntity.ok(new AuthResponse(token, username, nombre));
    
    }

    //Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody RegistroRequest request) {
        try {
            UsuarioEntity usuario = usuarioService.saveUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
    }
}
