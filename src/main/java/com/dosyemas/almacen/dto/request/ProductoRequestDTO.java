package com.dosyemas.almacen.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La categoría es obligatoria")
    private Integer idCategoria;

    @NotNull(message = "El stock mínimo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El stock mínimo debe ser mayor a cero")
    private BigDecimal stockMinimo;

    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida;
}