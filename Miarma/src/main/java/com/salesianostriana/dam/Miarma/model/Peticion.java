package com.salesianostriana.dam.Miarma.model;

import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Peticion implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String peticion;

    @ManyToOne
    @JoinColumn(name = "destino_usuario")
    private UserEntity destino;

    @ManyToOne
    @JoinColumn(name = "origen_usuario")
    private UserEntity origen;


    @PreRemove
    public void nullearDestinatarios(){
        origen.setSeguidor(null);
    }

}
