package com.salesianostriana.dam.Miarma.repository;

import com.salesianostriana.dam.Miarma.model.EstadoPublicacion;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {
    List<Publicacion> findByUser(UserEntity user);

    List<Publicacion> findByEstadoPublicacion(EstadoPublicacion estadoPublicacion);

    List<Publicacion> findAllByUserNick(String nick);


    @EntityGraph(value = "Publicacion-UserEntity")
    List<Publicacion> findByUserId(UUID id);

    @Query(value = """
            SELECT * FROM Publicacion p
             JOIN UserEntity u (u.id = p.user.id""",nativeQuery = true)
    List<Publicacion> findAllPostById(@Param("id") UUID id);

    @EntityGraph(value = "Publicacion-UserEntity")
    List<Publicacion> findOneByUserNick(String nick);

    @Query("""
            SELECT p FROM Publicacion p
            WHERE p.estadoPublicacion = :estado
            AND p.user.nick = :nick
            """)
    List<Publicacion> findPubli (@Param("estado") EstadoPublicacion estadoPublicacion, @Param("nick") String nick);
}
