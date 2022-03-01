package com.salesianostriana.dam.Miarma.security.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUsuarioResponse {

    private UUID id;
    private String nick;
    private String email;
    private String nombre;
    private String avatar;
    private String token;
}
