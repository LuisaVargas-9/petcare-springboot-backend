package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.DTO.ReviewRequest;
import com.petcare.backend.proyectoIntegrador.DTO.ReviewResponse;
import com.petcare.backend.proyectoIntegrador.entity.Review;
import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.repository.IReviewRepository;
import com.petcare.backend.proyectoIntegrador.repository.IServicioRepository;
import com.petcare.backend.proyectoIntegrador.repository.IUsuarioRepository;
import com.petcare.backend.proyectoIntegrador.repository.IReservaRepository;
import com.petcare.backend.proyectoIntegrador.service.IReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IReservaRepository reservaRepository;

    @Override
    public List<ReviewResponse> obtenerTodasLasReviews() {
        return reviewRepository.findAllActive().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse crearReview(ReviewRequest request) {
        if (reviewRepository.findByReservaIdReserva(request.getIdReserva()).isPresent()) {
            throw new RuntimeException("Ya existe una review para esta reserva");
        }

        Review review = new Review();
        review.setPuntuacion(request.getPuntuacion());
        review.setComentario(request.getComentario());
        review.setFechaCreacion(LocalDateTime.now());
        review.setEsBorrado(false);

        Reserva reserva = reservaRepository.findById(request.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!"COMPLETADA".equals(reserva.getEstado())) {
            throw new RuntimeException("Solo se pueden crear reviews para reservas completadas");
        }

        review.setReserva(reserva);
        review.setUsuario(reserva.getUsuario());
        review.setServicio(reserva.getServicio());

        Review savedReview = reviewRepository.save(review);
        actualizarRatingServicio(savedReview.getServicio().getIdServicio());

        return convertToResponse(savedReview);
    }

    @Override
    public ReviewResponse obtenerReviewPorId(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));
        return convertToResponse(review);
    }

    @Override
    public ReviewResponse actualizarReview(Integer id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));

        review.setPuntuacion(request.getPuntuacion());
        review.setComentario(request.getComentario());

        Review updatedReview = reviewRepository.save(review);
        actualizarRatingServicio(updatedReview.getServicio().getIdServicio());

        return convertToResponse(updatedReview);
    }

    @Override
    public void eliminarReview(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));
        review.setEsBorrado(true);
        reviewRepository.save(review);
        actualizarRatingServicio(review.getServicio().getIdServicio());
    }

    @Override
    public List<ReviewResponse> obtenerReviewsPorServicio(Integer idServicio) {
        return reviewRepository.findByServicioIdServicio(idServicio).stream()
                .filter(review -> !review.getEsBorrado())
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> obtenerReviewsPorUsuario(Integer idUsuario) {
        return reviewRepository.findByUsuarioIdUsuario(idUsuario).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ReviewResponse convertToResponse(Review review) {
        return new ReviewResponse(
                review.getIdReview(),
                review.getPuntuacion(),
                review.getComentario(),
                review.getFechaCreacion(),
                review.getUsuario().getNombre(),
                review.getServicio().getNombre(),
                review.getEsBorrado());
    }

    private void actualizarRatingServicio(Integer idServicio) {
        Double nuevoPromedio = reviewRepository.getAverageRatingByServicio(idServicio);
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setRating(nuevoPromedio != null ? nuevoPromedio : 0.0);
        servicioRepository.save(servicio);
    }
}
