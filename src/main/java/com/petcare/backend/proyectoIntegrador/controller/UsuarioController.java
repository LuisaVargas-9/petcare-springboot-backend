package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.DTO.UserProfileResponse;
import com.petcare.backend.proyectoIntegrador.config.JwtService;
import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final JwtService jwtService;
    private final IUsuarioService usuarioService;

    public UsuarioController(JwtService jwtService, IUsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.crear(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorIdResponse(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
//        return usuarioService.obtenerPorId(id)
//                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return new ResponseEntity<>(usuarioService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(usuarioService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Usuario>> listarPorRole(@PathVariable ERole role) {
        return new ResponseEntity<>(usuarioService.listarPorRole(role), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.obtenerPorId(id)
                .map(usuarioExistente -> {
                    usuario.setIdUsuario(id);
                    return new ResponseEntity<>(usuarioService.actualizar(usuario), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/{role}")
    public ResponseEntity<Usuario> actualizarRole(@PathVariable Integer id, @PathVariable ERole role) {
        return usuarioService.obtenerPorId(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setRole(role);
                    return new ResponseEntity<>(usuarioService.actualizar(usuarioExistente), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/usuario-list/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        System.out.println("Iniciando proceso de eliminación lógica para usuario ID: " + id);
        
        try {
            // Primero verificamos si el usuario existe
            Optional<Usuario> usuarioOptional = usuarioService.obtenerPorId(id);
            
            if (usuarioOptional.isEmpty()) {
                System.out.println("Usuario no encontrado con ID: " + id);
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = usuarioOptional.get();
            System.out.println("Usuario encontrado: " + usuario.getNombre() + " (ID: " + usuario.getIdUsuario() + ")");

            // Realizamos el borrado lógico
            usuarioService.eliminar(id);
            
            // Verificamos que el usuario haya sido marcado como borrado
            Optional<Usuario> verificacion = usuarioService.obtenerPorId(id);
            if (verificacion.isPresent() && !verificacion.get().isEsBorrado()) {
                System.out.println("Error: El usuario no fue marcado como borrado");
                return ResponseEntity.internalServerError()
                        .body(Map.of(
                            "mensaje", "Error: El usuario no pudo ser marcado como borrado",
                            "success", false
                        ));
            }

            System.out.println("Usuario marcado como borrado exitosamente");
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario eliminado correctamente");
            response.put("id", id);
            response.put("success", true);
            response.put("usuarioEliminado", usuario.getNombre());
            
            // Obtener la lista actualizada de usuarios
            List<Usuario> usuariosActivos = usuarioService.listarTodos();
            List<Map<String, Object>> usuariosActualizados = usuariosActivos.stream()
                .map(u -> {
                    Map<String, Object> usuarioMap = new HashMap<>();
                    usuarioMap.put("id", u.getIdUsuario());
                    usuarioMap.put("nombre", u.getNombre());
                    usuarioMap.put("apellido", u.getApellido());
                    usuarioMap.put("email", u.getEmail());
                    usuarioMap.put("rol", u.getRole().toString());
                    return usuarioMap;
                })
                .collect(Collectors.toList());
            
            response.put("usuariosActualizados", usuariosActualizados);
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("Error durante el borrado lógico: " + e.getMessage());
            e.printStackTrace();
            
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                        "mensaje", "Error al marcar el usuario como eliminado: " + e.getMessage(),
                        "success", false
                    ));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        System.out.println(email);
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        System.out.println(usuario.getEmail());
        System.out.println(usuario.getNombre());
        System.out.println(usuario.getRole());

        return ResponseEntity.ok(new UserProfileResponse(usuario));
    }

    @GetMapping("/usuario-list")
    public ResponseEntity<List<Map<String, Object>>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos(); // Este método ya filtra los usuarios activos
        List<Map<String, Object>> usuariosSimplificados = usuarios.stream()
            .map(usuario -> { //Transformo los objetos Usuarios en maps simples para evitar bucles infinitos
                Map<String, Object> usuarioMap = new HashMap<>();
                usuarioMap.put("id", usuario.getIdUsuario());
                usuarioMap.put("nombre", usuario.getNombre());
                usuarioMap.put("apellido", usuario.getApellido());
                usuarioMap.put("email", usuario.getEmail());
                usuarioMap.put("rol", usuario.getRole().toString());  // Convertimos el enum a String y usamos 'rol' para que coincida con el front
                return usuarioMap;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosSimplificados);
    }
} 