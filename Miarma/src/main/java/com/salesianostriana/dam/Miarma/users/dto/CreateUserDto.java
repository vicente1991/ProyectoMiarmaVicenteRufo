package com.salesianostriana.dam.Miarma.users.dto;

import com.salesianostriana.dam.Miarma.model.Publicacion;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String nombre;
    private String apellidos;
    private String nick;
    private String email;
    private Date fechaNacimiento;
    private String avatar;
    private String password;
    private String password2;

}
