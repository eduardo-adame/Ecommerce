package com.ceas.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ceas.proyecto.model.ProveedorEntity;

public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {

}
