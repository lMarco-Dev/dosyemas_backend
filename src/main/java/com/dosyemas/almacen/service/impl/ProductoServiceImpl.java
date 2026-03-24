package com.dosyemas.almacen.service.impl;

import com.dosyemas.almacen.dto.request.ProductoRequestDTO;
import com.dosyemas.almacen.dto.response.ProductoResponseDTO;
import com.dosyemas.almacen.entity.Categoria;
import com.dosyemas.almacen.entity.Producto;
import com.dosyemas.almacen.exception.ResourceNotFoundException;
import com.dosyemas.almacen.repository.CategoriaRepository;
import com.dosyemas.almacen.repository.ProductoRepository;
import com.dosyemas.almacen.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarActivos() {
        return productoRepository.findByActivoTrue()
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Integer id) {
        return productoRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Producto producto = Producto.builder()
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .categoria(categoria)
                .stockActual(BigDecimal.ZERO) // Al crear, el stock inicial es 0
                .stockMinimo(dto.getStockMinimo())
                .unidadMedida(dto.getUnidadMedida())
                .activo(true)
                .build();

        return toResponseDTO(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizar(Integer id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setUnidadMedida(dto.getUnidadMedida());
        producto.setCategoria(categoria);

        return toResponseDTO(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public void cambiarEstado(Integer id, Boolean estado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        producto.setActivo(estado);
        productoRepository.save(producto);
    }

    private ProductoResponseDTO toResponseDTO(Producto producto) {
        return ProductoResponseDTO.builder()
                .idProducto(producto.getIdProducto())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .categoria(producto.getCategoria().getNombre())
                .stockActual(producto.getStockActual())
                .stockMinimo(producto.getStockMinimo())
                .unidadMedida(producto.getUnidadMedida())
                .activo(producto.getActivo())
                .build();
    }
}