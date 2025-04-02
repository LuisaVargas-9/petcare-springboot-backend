package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.CaracteristicaValor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class CaracteristicaDTO {
        private Integer idCaracteristica;
        private String nombre;
        private String icon;
        private String valor;

        public CaracteristicaDTO() {}

        public CaracteristicaDTO(CaracteristicaValor caracteristicaValor) {
                this.idCaracteristica = caracteristicaValor.getIdCaracteristicaValor();
                this.nombre = caracteristicaValor.getCaracteristica().getNombre();
                this.valor = caracteristicaValor.getValor();
                this.icon = caracteristicaValor.getCaracteristica().getIcon();
        }
}

