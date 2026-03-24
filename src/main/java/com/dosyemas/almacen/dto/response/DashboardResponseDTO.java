package com.dosyemas.almacen.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {
    private Long totalProductosActivos;
    private Long movimientosHoy;
    private Long notificacionesNoLeidas;
    private List<MovimientoResponseDTO> ultimosMovimientos;
    private Long totalStockBajo;
    private List<ProductoResponseDTO> itemsCriticos;
}