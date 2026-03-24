package com.dosyemas.almacen.service;

import com.dosyemas.almacen.dto.request.ProductoRequestDTO;
import com.dosyemas.almacen.dto.response.ProductoResponseDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoResponseDTO> listarActivos();
    ProductoResponseDTO buscarPorId(Integer id);
    ProductoResponseDTO crear(ProductoRequestDTO dto);
    ProductoResponseDTO actualizar(Integer id, ProductoRequestDTO dto);
    void cambiarEstado(Integer id, Boolean estado);
}