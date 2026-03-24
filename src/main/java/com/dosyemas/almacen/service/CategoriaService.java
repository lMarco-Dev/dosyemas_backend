package com.dosyemas.almacen.service;

import com.dosyemas.almacen.dto.request.CategoriaRequestDTO;
import com.dosyemas.almacen.dto.response.CategoriaResponseDTO;

import java.util.List;

// 1. La interfaz — define el CONTRATO (qué puede hacer)
public interface CategoriaService {

    List<CategoriaResponseDTO> listarTodas();
    CategoriaResponseDTO crear(CategoriaRequestDTO dto);
    CategoriaResponseDTO actualizar(Integer id, CategoriaRequestDTO dto);
    void eliminar(Integer id);

}
