package com.salesianostriana.dam.Miarma.repository;

import com.salesianostriana.dam.Miarma.model.EstadoPublicacion;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {
    List<Publicacion> findByUser(UserEntity user);
    List<Publicacion> findByEstadoPublicacion(EstadoPublicacion estadoPublicacion);
}
