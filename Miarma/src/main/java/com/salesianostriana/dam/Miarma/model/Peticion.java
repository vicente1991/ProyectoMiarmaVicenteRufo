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
    @JoinColumn(name = "recibido_usuario")
    private UserEntity recibido;


    @PreRemove
    public void nullearDestinatarios(){
        recibido.setSiguiendo(null);
    }
    public void addDestinatario(UserEntity u) {
        this.recibido = u;
        u.getSiguiendo().add(this);
    }

    public void removeDestinatario(UserEntity u) {
        u.getSiguiendo().remove(this);
        this.recibido = null;
    }
}
