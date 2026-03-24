package com.dosyemas.almacen.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponseDTO {
    private Integer idCategoria;
    private String nombre;
    private String descripcion;
}
