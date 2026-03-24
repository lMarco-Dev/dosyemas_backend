package com.dosyemas.almacen.service;

import com.dosyemas.almacen.dto.response.DashboardResponseDTO;

public interface DashboardService {
    DashboardResponseDTO obtenerResumen(Integer idUsuario);
}