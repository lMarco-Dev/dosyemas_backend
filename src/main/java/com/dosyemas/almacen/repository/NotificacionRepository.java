package com.dosyemas.almacen.repository;

import com.dosyemas.almacen.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    // Obtener todas las notificaciones de un usuario
    List<Notificacion> findByUsuarioIdUsuario(Integer idUsuario);

    // Filtrar las no leídas (El método que te está pidiendo el IDE)
    List<Notificacion> findByUsuarioIdUsuarioAndLeidaFalse(Integer idUsuario);

    // Cantidad de notificaciones no leídas (para el badge del dashboard)
    Long countByUsuarioIdUsuarioAndLeidaFalse(Integer idUsuario);
}
