package com.dosyemas.almacen.service.impl;

import com.dosyemas.almacen.dto.request.CategoriaRequestDTO;
import com.dosyemas.almacen.dto.response.CategoriaResponseDTO;
import com.dosyemas.almacen.entity.Categoria;
import com.dosyemas.almacen.exception.ResourceNotFoundException;
import com.dosyemas.almacen.repository.CategoriaRepository;
import com.dosyemas.almacen.service.CategoriaService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaResponseDTO  crear(CategoriaRequestDTO dto) {
        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        Categoria guardada = categoriaRepository.save(categoria);
        return toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO actualizar(Integer id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        return toResponseDTO(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        categoriaRepository.delete(categoria);
    }

    /* ------------------------------------------------------------
                            MAPPER PRIVADO
       ------------------------------------------------------------ */
    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return CategoriaResponseDTO.builder()
                .idCategoria(categoria.getIdCategoria())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }
}
