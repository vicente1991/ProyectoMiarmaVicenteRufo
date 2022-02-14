package com.salesianostriana.dam.Miarma.users.dto;

import lombok.*;

import java.util.Date;
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
}
