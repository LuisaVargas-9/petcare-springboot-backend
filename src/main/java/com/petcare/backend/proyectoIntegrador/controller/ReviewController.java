package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.DTO.ReviewRequest;
import com.petcare.backend.proyectoIntegrador.DTO.ReviewResponse;
import com.petcare.backend.proyectoIntegrador.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> obtenerTodasLasReviews() {
        return ResponseEntity.ok(reviewService.obtenerTodasLasReviews());
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> crearReview(@RequestBody ReviewRequest request) {
        return new ResponseEntity<>(reviewService.crearReview(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> obtenerReviewPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(reviewService.obtenerReviewPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> actualizarReview(
            @PathVariable Integer id,
            @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.actualizarReview(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReview(@PathVariable Integer id) {
        reviewService.eliminarReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<List<ReviewResponse>> obtenerReviewsPorServicio(
            @PathVariable Integer idServicio) {
        return ResponseEntity.ok(reviewService.obtenerReviewsPorServicio(idServicio));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReviewResponse>> obtenerReviewsPorUsuario(
            @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reviewService.obtenerReviewsPorUsuario(idUsuario));
    }

    @GetMapping("/promedio/{idServicio}")
    public ResponseEntity<Double> obtenerPromedioPuntuacion(@PathVariable Integer idServicio) {
        Double promedio = reviewService.obtenerPromedioPuntuacionPorServicio(idServicio);
        return ResponseEntity.ok(promedio);
    }
}
