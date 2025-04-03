package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.DTO.ReviewRequest;
import com.petcare.backend.proyectoIntegrador.DTO.ReviewResponse;
import java.util.List;

public interface IReviewService {
    List<ReviewResponse> obtenerTodasLasReviews();

    ReviewResponse crearReview(ReviewRequest reviewRequest);

    ReviewResponse obtenerReviewPorId(Integer id);

    ReviewResponse actualizarReview(Integer id, ReviewRequest reviewRequest);

    void eliminarReview(Integer id);

    List<ReviewResponse> obtenerReviewsPorServicio(Integer idServicio);

    List<ReviewResponse> obtenerReviewsPorUsuario(Integer idUsuario);
}
