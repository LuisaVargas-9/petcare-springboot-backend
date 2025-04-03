package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.ReservaFecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IReservaFechaRepository extends JpaRepository<ReservaFecha, Integer> {

   @Modifying
   @Transactional
   @Query("DELETE FROM ReservaFecha rf WHERE rf.reserva.idReserva = :idReserva")
   void deleteByIdReserva(@Param("idReserva") int idReserva);

}
