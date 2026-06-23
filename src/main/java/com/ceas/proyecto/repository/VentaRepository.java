package com.ceas.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ceas.proyecto.model.VentaEntity;

public interface VentaRepository extends JpaRepository<VentaEntity, Long> {

}
