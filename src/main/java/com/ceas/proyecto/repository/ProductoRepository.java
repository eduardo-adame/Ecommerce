package com.ceas.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ceas.proyecto.model.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

}
