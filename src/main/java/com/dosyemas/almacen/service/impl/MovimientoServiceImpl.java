package com.dosyemas.almacen.service.impl;

import com.dosyemas.almacen.dto.request.MovimientoRequestDTO;
import com.dosyemas.almacen.dto.response.MovimientoResponseDTO;
import com.dosyemas.almacen.entity.Movimiento;
import com.dosyemas.almacen.entity.Notificacion;
import com.dosyemas.almacen.entity.Producto;
import com.dosyemas.almacen.entity.TipoMovimiento;
import com.dosyemas.almacen.entity.Usuario;
import com.dosyemas.almacen.exception.ResourceNotFoundException;
import com.dosyemas.almacen.exception.StockInsuficienteException;
import com.dosyemas.almacen.repository.MovimientoRepository;
import com.dosyemas.almacen.repository.NotificacionRepository;
import com.dosyemas.almacen.repository.ProductoRepository;
import com.dosyemas.almacen.repository.UsuarioRepository;
import com.dosyemas.almacen.service.MovimientoService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacionRepository notificacionRepository; // Inyectado para las alertas

    @Override
    @Transactional
    public MovimientoResponseDTO registrar(MovimientoRequestDTO dto, Integer idUsuario) {

        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + dto.getIdProducto()));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        if (dto.getTipo() == TipoMovimiento.SALIDA) {
            if (producto.getStockActual().compareTo(dto.getCantidad()) < 0) {
                throw new StockInsuficienteException("Stock insuficiente. Stock actual: "
                        + producto.getStockActual() + ", Cantidad solicitada: " + dto.getCantidad());
            }
            producto.setStockActual(producto.getStockActual().subtract(dto.getCantidad()));
        } else if (dto.getTipo() == TipoMovimiento.ENTRADA) {
            producto.setStockActual(producto.getStockActual().add(dto.getCantidad()));
        }

        productoRepository.save(producto);

        // LÓGICA DE NOTIFICACIÓN AUTOMÁTICA
        if (producto.getStockActual().compareTo(producto.getStockMinimo()) <= 0) {
            Notificacion notificacion = Notificacion.builder()
                    .producto(producto)
                    .usuario(usuario)
                    .mensaje("Stock crítico: " + producto.getNombre()
                            + " tiene " + producto.getStockActual()
                            + " " + producto.getUnidadMedida() + " disponibles")
                    .leida(false)
                    .build();
            notificacionRepository.save(notificacion);
        }

        Movimiento movimiento = Movimiento.builder()
                .tipo(dto.getTipo())
                .cantidad(dto.getCantidad())
                .motivo(dto.getMotivo())
                .documentoReferencia(dto.getDocumentoReferencia())
                .producto(producto)
                .usuario(usuario)
                .build();

        Movimiento guardado = movimientoRepository.save(movimiento);
        return toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> listarPorProducto(Integer idProducto) {
        return movimientoRepository.findByProductoIdProductoOrderByFechaDesc(idProducto)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> listarDeHoy() {
        return movimientoRepository.findMovimientosDeHoy()
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private MovimientoResponseDTO toResponseDTO(Movimiento movimiento) {
        return MovimientoResponseDTO.builder()
                .idMovimiento(movimiento.getIdMovimiento())
                .tipo(movimiento.getTipo().name())
                .productoNombre(movimiento.getProducto().getNombre())
                .productoCodigo(movimiento.getProducto().getCodigo())
                .cantidad(movimiento.getCantidad())
                .usuarioResponsable(movimiento.getUsuario().getNombre() + " " + movimiento.getUsuario().getApellido())
                .motivo(movimiento.getMotivo())
                .documentoReferencia(movimiento.getDocumentoReferencia())
                .fecha(movimiento.getFecha())
                .build();
    }
}