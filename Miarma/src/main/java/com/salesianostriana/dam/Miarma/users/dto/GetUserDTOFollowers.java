package com.salesianostriana.dam.Miarma.users.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDTOFollowers {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String nick;
    private LocalDate fechaNacimiento;
    private String email;
    private String avatar;
    private List<String> followers;
    private int peticiones;
}
