package com.salesianostriana.dam.Miarma.model;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Publicacion {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String titulo;

    private String texto;

    private String imagen;

    @DateTimeFormat
    private Date fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPublicacion estadoPublicacion;
}
