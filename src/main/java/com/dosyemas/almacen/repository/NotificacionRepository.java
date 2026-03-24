package com.dosyemas.almacen.repository;

import com.dosyemas.almacen.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    //Obtener notificaciones de un usuario en especifico:
    List<Notificacion> findByUsuarioIdUsuario(Integer idUsuario);

    //Filtrar no leidas
    List<Notificacion> findByUsuarioIdUsuarioAndLeida(Integer idUsuario, Boolean leida);

    // Cantidad - Notificaciones no leidas
    Long countByUsuarioIdUsuarioAndLeidaFalse(Integer idUsuario);
}
