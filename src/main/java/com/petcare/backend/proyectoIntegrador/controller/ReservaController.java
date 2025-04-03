package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.DTO.ReservaDTO;
import com.petcare.backend.proyectoIntegrador.DTO.ReservaResponse;
import com.petcare.backend.proyectoIntegrador.config.JwtService;
import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.entity.ReservaFecha;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.service.IReservaService;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final IReservaService reservaService;
    private final IUsuarioService usuarioService;
    private final IServicioService servicioService;
    private final JwtService jwtService;

    public ReservaController(IReservaService reservaService, IUsuarioService usuarioService,
            IServicioService servicioService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.servicioService = servicioService;
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.crear(reserva), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> obtenerPorId(@PathVariable Integer id) {
        return reservaService.obtenerPorId(id)
                .map(reserva -> new ResponseEntity<>(new ReservaResponse(reserva), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarTodos(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();

        List<ReservaResponse> response = reservaService
                .listarPorUsuario(usuario.getIdUsuario())
                .stream()
                .map(ReservaResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return new ResponseEntity<>(reservaService.listarPorUsuario(usuarioId), HttpStatus.OK);
    }

//    @GetMapping("/mascota/{mascotaId}")
//    public ResponseEntity<List<Reserva>> listarPorMascota(@PathVariable Integer mascotaId) {
//        return new ResponseEntity<>(reservaService.listarPorMascota(mascotaId), HttpStatus.OK);
//    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reserva>> listarPorEstado(@PathVariable String estado) {
        return new ResponseEntity<>(reservaService.listarPorEstado(estado), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(@PathVariable Integer id, @RequestBody Reserva reserva) {
        return reservaService.obtenerPorId(id)
                .map(reservaExistente -> {
                    reserva.setIdReserva(id);
                    return new ResponseEntity<>(reservaService.actualizar(reserva), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (reservaService.obtenerPorId(id).isPresent()) {
            reservaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{servicioId}")
    public ResponseEntity<String> reservar(@RequestHeader("Authorization") String token,
                                           @PathVariable Integer servicioId) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        Servicio servicio = servicioService.obtenerPorId(servicioId).orElseThrow();

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setServicio(servicio);

        List<ReservaFecha> fechaR = new ArrayList<>();

        reservaService.crear(reserva);
        return ResponseEntity.ok("Reservación realizada");
    }



    @GetMapping("/{idServicio}/fechas-reservas")
    public ResponseEntity<List<LocalDate>> getReservas(@PathVariable("idServicio") Integer idServicio) {
        System.out.println("Solicitud recibida para idServicio: " + idServicio);

        List<LocalDate> reservas = reservaService.getFechasConfirmadas(idServicio);
        return ResponseEntity.ok(reservas);
    }

    @PostMapping("/reserva")
    public ResponseEntity<Reserva> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva nuevaReserva = reservaService.crearReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }


    @PutMapping("/{idReserva}/editar")
    public ResponseEntity<ReservaResponse> editarFechasReserva(@PathVariable int idReserva, @RequestBody ReservaDTO reservaEditar) {
        Reserva reserva = reservaService.editarReserva(idReserva, reservaEditar);
        if(reserva != null) {
            ReservaResponse reservaEditada = new ReservaResponse(reserva);
            return ResponseEntity.status(HttpStatus.OK).body(reservaEditada);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{idReserva}/cancelar")
    public ResponseEntity<String> cancelarReserva(@PathVariable int idReserva) {
        String respuestaReserva = reservaService.cancelarReserva(idReserva);
        if(respuestaReserva != null) {
            return ResponseEntity.status(HttpStatus.OK).body(respuestaReserva);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
