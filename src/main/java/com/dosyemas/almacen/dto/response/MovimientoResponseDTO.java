package com.dosyemas.almacen.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoResponseDTO {
    private Integer idMovimiento;
    private String tipo;
    private String productoNombre;
    private String productoCodigo;
    private BigDecimal cantidad;
    private String usuarioResponsable; // Solo el nombre del usuario
    private String motivo;
    private String documentoReferencia;
    private LocalDateTime fecha;
}