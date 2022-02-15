package com.salesianostriana.dam.Miarma.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUsuarioResponse {

    private String nick;
    private String email;
    private String nombre;
    private String avatar;
    private String token;
}
