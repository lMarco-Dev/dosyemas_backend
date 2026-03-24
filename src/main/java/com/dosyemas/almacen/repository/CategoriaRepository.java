package com.dosyemas.almacen.repository;

import com.dosyemas.almacen.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    // Útil para validar que no se cree una categoría que ya existe
    Optional<Categoria> findByNombre(String nombre);
}