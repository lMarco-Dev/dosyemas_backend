package com.dosyemas.almacen.controller;

import com.dosyemas.almacen.dto.response.NotificacionResponseDTO;
import com.dosyemas.almacen.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class NotificacionController {

    private final NotificacionService notificacionService;

    // 1. GET /api/v1/notificaciones/usuario/{idUsuario}
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionResponseDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(idUsuario));
    }

    // 2. PATCH /api/v1/notificaciones/{id}/leida
    @PatchMapping("/{id}/leida")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Integer id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok().build();
    }

    // 3. PATCH /api/v1/notificaciones/usuario/{idUsuario}/marcar-todas
    @PatchMapping("/usuario/{idUsuario}/marcar-todas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Integer idUsuario) {
        notificacionService.marcarTodasComoLeidas(idUsuario);
        return ResponseEntity.ok().build();
    }
}