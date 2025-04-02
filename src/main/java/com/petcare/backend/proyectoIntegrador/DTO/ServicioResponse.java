package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.CaracteristicaValor;
import com.petcare.backend.proyectoIntegrador.entity.Categoria;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.entity.ServicioImagen;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicioResponse {
    private Integer idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private List<ServicioImagen> imagenUrls;
    //private Boolean esDisponible;
//    private LocalDateTime fechaRegistro;
//    private LocalDateTime fechaActualizacion;
private boolean esBorrado;
    //private LocalDateTime fechaBorrado;
    private Categoria categoria;
    private Double rating;
    private List<CaracteristicaDTO> caracteristicas;

    public ServicioResponse(Servicio servicio) {
        this.idServicio = servicio.getIdServicio();
        this.nombre = servicio.getNombre();
        this.descripcion = servicio.getDescripcion();
        this.precio = servicio.getPrecio();
        this.imagenUrls = servicio.getImagenUrls();
        this.esBorrado = servicio.isEsBorrado();
        this.categoria = servicio.getCategoria();
        this.rating = servicio.getRating();
        this.caracteristicas = servicio.getCaracteristicas().stream()
                .map(CaracteristicaDTO::new)
                .collect(Collectors.toList());
    }
}
