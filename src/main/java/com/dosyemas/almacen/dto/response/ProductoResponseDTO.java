package com.dosyemas.almacen.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Integer idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria; // Solo el nombre de la categoría
    private BigDecimal stockActual;
    private BigDecimal stockMinimo;
    private String unidadMedida;
    private Boolean activo;
}