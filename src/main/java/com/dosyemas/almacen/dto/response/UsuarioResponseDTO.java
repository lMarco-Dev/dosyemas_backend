package com.dosyemas.almacen.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Integer idUsuario;
    private String nombreCompleto; // Unificamos nombre + apellido para la vista
    private String email;
    private String rol; // Solo el nombre del rol (ej: "SUPERVISOR")
    private Integer idRol;     // Para editar — saber cuál seleccionar en el combo
    private Boolean activo;
}