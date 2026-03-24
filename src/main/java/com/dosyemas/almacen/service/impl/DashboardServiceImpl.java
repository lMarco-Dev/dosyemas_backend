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

        // 1. Obtener métricas rápidas
        long totalProductos = productoRepository.findByActivoTrue().size();
        long movimientosHoy = movimientoRepository.findMovimientosDeHoy().size();
        long notificacionesNoLeidas = notificacionRepository.countByUsuarioIdUsuarioAndLeidaFalse(idUsuario);

        // 2. Obtener listas para el dashboard
        List<MovimientoResponseDTO> ultimosMovimientos = movimientoRepository.findMovimientosDeHoy()
                .stream().limit(5).map(this::mapMovimiento).collect(Collectors.toList());

        // Reutilizamos la query JPQL que creaste en la Fase 1
        List<ProductoResponseDTO> itemsCriticos = productoRepository.findProductosConStockCritico()
                .stream().map(p -> ProductoResponseDTO.builder()
                        .idProducto(p.getIdProducto())
                        .codigo(p.getCodigo())
                        .nombre(p.getNombre())
                        .stockActual(p.getStockActual())
                        .stockMinimo(p.getStockMinimo())
                        .build())
                .collect(Collectors.toList());

        // 3. Ensamblar la respuesta
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