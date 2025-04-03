package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {
    // Solo métodos de Review
    @Query("SELECT r FROM Review r WHERE r.servicio.idServicio = :idServicio AND r.esBorrado = false")
    List<Review> findByServicioIdServicio(@Param("idServicio") Integer idServicio);

    @Query("SELECT r FROM Review r WHERE r.usuario.idUsuario = :idUsuario AND r.esBorrado = false")
    List<Review> findByUsuarioIdUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT r FROM Review r WHERE r.reserva.idReserva = :idReserva AND r.esBorrado = false")
    Optional<Review> findByReservaIdReserva(@Param("idReserva") Integer idReserva);

    @Query("SELECT COALESCE(AVG(r.puntuacion), 0.0) FROM Review r WHERE r.servicio.idServicio = :idServicio AND r.esBorrado = false")
    Double getAverageRatingByServicio(@Param("idServicio") Integer idServicio);

    @Query("SELECT r FROM Review r WHERE r.esBorrado = false ORDER BY r.fechaCreacion DESC")
    List<Review> findAllActive();
}
