package com.dosyemas.almacen.service;

import com.dosyemas.almacen.dto.response.NotificacionResponseDTO;

import java.util.List;

public interface NotificacionService {

    List<NotificacionResponseDTO> listarPorUsuario(Integer idUsuario);
    void marcarComoLeida(Integer idNotificacion);
    void marcarTodasComoLeidas(Integer idUsuario);
}
