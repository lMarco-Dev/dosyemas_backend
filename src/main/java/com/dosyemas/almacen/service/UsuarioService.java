package com.dosyemas.almacen.service;

import com.dosyemas.almacen.dto.request.UsuarioRequestDTO;
import com.dosyemas.almacen.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> listarTodos();
    UsuarioResponseDTO buscarPorId(Integer id);
    UsuarioResponseDTO crear(UsuarioRequestDTO dto);
    UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO dto);
    void cambiarEstado(Integer id, Boolean estado);
}