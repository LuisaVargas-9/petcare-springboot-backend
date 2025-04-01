package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.entity.ReservaFecha;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReservaResponse {

    private Integer idReserva;
    private String nombreServicio;
    private String imagenServicio;
    private String estado;
    private boolean esBorrado;
    private LocalDateTime fechaBorrado;
    private List<ReservaFecha> fechas;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String nombreCategoria;


    public ReservaResponse(Reserva reserva) {
        this.idReserva = reserva.getIdReserva();
        this.nombreServicio = reserva.getServicio().getNombre();
        this.estado = reserva.getEstado();
        this.esBorrado = reserva.isEsBorrado();
        this.fechaBorrado = reserva.getFechaBorrado();
        this.fechas = reserva.getFechas();

        if (reserva.getServicio().getCategoria() != null) {
            this.nombreCategoria = reserva.getServicio().getCategoria().getNombre();
        }

        if (fechas != null && !fechas.isEmpty()) {
            this.fechaInicio = fechas.stream()
                    .map(ReservaFecha::getFecha)
                    .min(LocalDate::compareTo)
                    .orElse(null);

            this.fechaFin = fechas.stream()
                    .map(ReservaFecha::getFecha)
                    .max(LocalDate::compareTo)
                    .orElse(null);
        }

        if (reserva.getServicio().getImagenUrls() != null && !reserva.getServicio().getImagenUrls().isEmpty()) {
            this.imagenServicio = reserva.getServicio().getImagenUrls().get(0).getImagenUrl();
        }
    }

    public Integer getId() { return idReserva; }
    public void setId(Integer id) { this.idReserva = id; }
}
