package com.ceas.proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "categoria")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private long productoId;
}
