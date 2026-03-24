package com.dosyemas.almacen.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponseDTO {
    private Integer idNotificacion;
    private String productoNombre; // Para saber rápidamente qué producto tiene el problema
    private String mensaje;
    private Boolean leida;
    private LocalDateTime fechaCreacion;
}