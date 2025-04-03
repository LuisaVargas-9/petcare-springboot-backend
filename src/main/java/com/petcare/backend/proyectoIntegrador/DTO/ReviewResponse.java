package com.petcare.backend.proyectoIntegrador.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Integer idReview;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fechaCreacion;
    private String nombreUsuario;
    private String servicioNombre;
    private Boolean esBorrado;
}
