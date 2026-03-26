package com.dosyemas.almacen.controller;

import com.dosyemas.almacen.dto.response.DashboardResponseDTO;
import com.dosyemas.almacen.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    // GET /api/v1/dashboard/resumen
    @GetMapping("/resumen")
    public ResponseEntity<DashboardResponseDTO> obtenerResumen() {
        // TODO: Extraer idUsuario del token JWT en la capa de seguridad
        Integer idUsuarioTemporal = 1;
        return ResponseEntity.ok(dashboardService.obtenerResumen(idUsuarioTemporal));
    }
}