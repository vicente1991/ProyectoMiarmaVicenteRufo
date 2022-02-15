package com.salesianostriana.dam.Miarma.users.dto;

import com.salesianostriana.dam.Miarma.model.Publicacion;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String nick;
    private Date fechaNacimiento;
    private String email;
    private String avatar;
    private List<Publicacion> numPublicaciones;
    private int numSeguidores;
    private int numSiguiendo;
}
