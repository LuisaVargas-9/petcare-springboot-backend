package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    @Query("SELECT s FROM Servicio s WHERE (:categoria IS NULL OR s.categoria = :categoria)")
    List<Servicio> findByCategoria(@Param("categoria") String categoria);


    @Query("SELECT s FROM Servicio s WHERE s.nombre LIKE %:nombre%")
    List<Servicio> findByNombre(String nombre);
    
    List<Servicio> findByEsDisponible(Boolean esDisponible);
    
    @Query("SELECT s FROM Servicio s WHERE s.precio <= :precioMaximo")
    List<Servicio> findByPrecioLessThanEqual(BigDecimal precioMaximo);

    @Query("SELECT new com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse(s) " +
            "FROM Servicio s LEFT JOIN FETCH s.categoria WHERE s.esBorrado = false")
    List<ServicioResponse> findActivos();

    @Query("SELECT s.nombre FROM Servicio s WHERE LOWER(s.nombre) LIKE LOWER(CONCAT('%', :query, '%')) AND s.esBorrado = false")
    List<String> findSuggestionsByName(@Param("query") String query);

    @Query("SELECT s FROM Servicio s WHERE (:categoriaIds IS NULL OR s.categoria.id_categoria IN :categoriaIds)")
    List<ServicioResponse> findByCategoriaIds(@Param("categoriaIds") List<Long> categoriaIds);


} 