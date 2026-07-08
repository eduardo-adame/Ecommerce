package com.ceas.proyecto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ceas.proyecto.model.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);
    boolean existsByUsername(String username);

}
