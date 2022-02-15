package com.salesianostriana.dam.Miarma.model;


import com.salesianostriana.dam.Miarma.users.model.UserEntity;
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
    @GeneratedValue
    private Long id;

    private String titulo;

    private String texto;

    private String imagen;

    @DateTimeFormat
    private Date fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPublicacion estadoPublicacion;

    @ManyToOne
    private UserEntity user;

    //HELPERS

    public void addUser(UserEntity u) {
        this.user = u;
        u.getPublicaciones().add(this);
    }

    public void removeUser(UserEntity u) {
        u.getPublicaciones().remove(this);
        this.user = null;
    }
}
