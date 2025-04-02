package com.petcare.backend.proyectoIntegrador.util;

import com.petcare.backend.proyectoIntegrador.DTO.CaracteristicaDTO;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioSinCategoriaResponse;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;

import java.util.List;

public class DtoConverter {

    public static ServicioResponse convertirARespuesta(Servicio servicio) {
        List<CaracteristicaDTO> caracteristicas = convertCaracteristicas(servicio);

        return new ServicioResponse(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getImagenUrls(),
                servicio.isEsBorrado(),
                servicio.getCategoria(),
                servicio.getRating(),
                caracteristicas
                //servicio.getEsDisponible(),
                //servicio.getFechaRegistro(),
                //servicio.getFechaActualizacion(),
                //servicio.getFechaBorrado(),
        );
    }

    public static ServicioSinCategoriaResponse convertirARespuestaSinCategoria(Servicio servicio) {
        List<CaracteristicaDTO> caracteristicas = convertCaracteristicas(servicio);

        return new ServicioSinCategoriaResponse(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getImagenUrls(),
                servicio.getRating(),
                caracteristicas
               // servicio.getEsDisponible(),
               // servicio.getFechaRegistro(),
               // servicio.getFechaActualizacion(),
               // servicio.isEsBorrado(),
               // servicio.getFechaBorrado(),

        );
    }

    private static List<CaracteristicaDTO> convertCaracteristicas(Servicio servicio) {
        List<CaracteristicaDTO> caracteristicas = servicio.getCaracteristicas().stream()
                .map(cv -> {
                    CaracteristicaDTO dto = new CaracteristicaDTO();
                    dto.setIdCaracteristica(cv.getCaracteristica().getIdCaracteristica());
                    dto.setNombre(cv.getCaracteristica().getNombre());
                    dto.setValor(cv.getValor());
                    return dto;
                })
                .toList();
        return caracteristicas;
    }

}
