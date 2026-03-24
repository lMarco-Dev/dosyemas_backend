package com.dosyemas.almacen.service;

import com.dosyemas.almacen.dto.request.MovimientoRequestDTO;
import com.dosyemas.almacen.dto.response.MovimientoResponseDTO;
import java.util.List;

public interface MovimientoService {
    MovimientoResponseDTO registrar(MovimientoRequestDTO dto, Integer idUsuario);
    List<MovimientoResponseDTO> listarPorProducto(Integer idProducto);
    List<MovimientoResponseDTO> listarDeHoy();
}