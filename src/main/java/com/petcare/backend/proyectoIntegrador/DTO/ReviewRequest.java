package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewRequest {
    private Integer puntuacion;
    private String comentario;
    private Integer idReserva;
    private Integer idUsuario;
    private Integer idServicio;
}