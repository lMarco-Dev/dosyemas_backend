package com.dosyemas.almacen.service.impl;

import com.dosyemas.almacen.dto.response.DashboardResponseDTO;
import com.dosyemas.almacen.dto.response.MovimientoResponseDTO;
import com.dosyemas.almacen.dto.response.ProductoResponseDTO;
import com.dosyemas.almacen.entity.Movimiento;
import com.dosyemas.almacen.repository.MovimientoRepository;
import com.dosyemas.almacen.repository.NotificacionRepository;
import com.dosyemas.almacen.repository.ProductoRepository;
import com.dosyemas.almacen.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ProductoRepository productoRepository;
    private final MovimientoRepository movimientoRepository;
    private final NotificacionRepository notificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponseDTO obtenerResumen(Integer idUsuario) {

        long totalProductos = productoRepository.countByActivoTrue();

        long notificacionesNoLeidas = notificacionRepository.countByUsuarioIdUsuarioAndLeidaFalse(idUsuario);

        List<Movimiento> movimientosDeHoy = movimientoRepository.findMovimientosDeHoy();
        long movimientosHoy = movimientosDeHoy.size(); // Contamos la lista que ya está en memoria

        // Reutilizamos la misma lista para sacar los 5 últimos sin volver a consultar a la BD
        List<MovimientoResponseDTO> ultimosMovimientos = movimientosDeHoy
                .stream()
                .limit(5)
                .map(this::mapMovimiento)
                .collect(Collectors.toList());

        // Reutilizamos la query JPQL para productos críticos
        List<ProductoResponseDTO> itemsCriticos = productoRepository.findProductosConStockCritico()
                .stream().map(p -> ProductoResponseDTO.builder()
                        .idProducto(p.getIdProducto())
                        .codigo(p.getCodigo())
                        .nombre(p.getNombre())
                        .stockActual(p.getStockActual())
                        .stockMinimo(p.getStockMinimo())
                        .build())
                .collect(Collectors.toList());

        // Ensamblar la respuesta
        return DashboardResponseDTO.builder()
                .totalProductosActivos(totalProductos)
                .movimientosHoy(movimientosHoy)
                .notificacionesNoLeidas(notificacionesNoLeidas)
                .totalStockBajo((long) itemsCriticos.size())
                .ultimosMovimientos(ultimosMovimientos)
                .itemsCriticos(itemsCriticos)
                .build();
    }

    private MovimientoResponseDTO mapMovimiento(Movimiento movimiento) {
        return MovimientoResponseDTO.builder()
                .idMovimiento(movimiento.getIdMovimiento())
                .tipo(movimiento.getTipo().name())
                .productoNombre(movimiento.getProducto().getNombre())
                .cantidad(movimiento.getCantidad())
                .fecha(movimiento.getFecha())
                .build();
    }
}