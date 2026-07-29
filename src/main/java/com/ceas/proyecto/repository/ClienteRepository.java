package com.ceas.proyecto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ceas.proyecto.model.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByUsername(String username);
}
