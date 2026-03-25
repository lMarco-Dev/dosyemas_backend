package com.dosyemas.almacen.service.impl;

import com.dosyemas.almacen.dto.response.NotificacionResponseDTO;
import com.dosyemas.almacen.entity.Notificacion;
import com.dosyemas.almacen.exception.ResourceNotFoundException;
import com.dosyemas.almacen.repository.NotificacionRepository;
import com.dosyemas.almacen.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> listarPorUsuario(Integer idUsuario) {
        return notificacionRepository.findByUsuarioIdUsuarioAndLeidaFalse(idUsuario)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void marcarComoLeida(Integer idNotificacion) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);
    }

    @Override
    @Transactional
    public void marcarTodasComoLeidas(Integer idUsuario) {
        List<Notificacion> noLeidas = notificacionRepository.findByUsuarioIdUsuarioAndLeidaFalse(idUsuario);
        noLeidas.forEach(n -> n.setLeida(true));
        notificacionRepository.saveAll(noLeidas); // saveAll es mucho más eficiente que iterar un save()
    }

    private NotificacionResponseDTO toResponseDTO(Notificacion notificacion) {
        return NotificacionResponseDTO.builder()
                .idNotificacion(notificacion.getIdNotificacion())
                .productoNombre(notificacion.getProducto() != null ? notificacion.getProducto().getNombre() : "Sistema")
                .mensaje(notificacion.getMensaje())
                .leida(notificacion.getLeida())
                .fechaCreacion(notificacion.getFechaCreacion())
                .build();
    }
}
