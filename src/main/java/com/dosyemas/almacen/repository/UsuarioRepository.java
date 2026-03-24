package com.dosyemas.almacen.repository;

import com.dosyemas.almacen.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Alternativa para login o recuperaciones de contraseña
    Optional<Usuario> findByEmail(String email);

    // Validar si un correo ya está registrado antes de crear un nuevo usuario
    Boolean existsByEmail(String email);

    // Listar usuarios por su rol (Ej: listar todos los 'SUPERVISOR')
    List<Usuario> findByRolNombre(String nombreRol);

    // Listar usuarios activos
    List<Usuario> findByActivoTrue();
}