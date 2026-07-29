package com.ceas.proyecto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ceas.proyecto.model.VentaEntity;

public interface VentaRepository extends JpaRepository<VentaEntity, Long> {
    List<VentaEntity> findByClienteId(Long clienteId);
}
