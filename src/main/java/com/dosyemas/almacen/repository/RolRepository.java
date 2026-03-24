package com.dosyemas.almacen.repository;

import com.dosyemas.almacen.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    // Buscar usuario por rol
    Optional<Rol> findByNombre(String nombre);
}
