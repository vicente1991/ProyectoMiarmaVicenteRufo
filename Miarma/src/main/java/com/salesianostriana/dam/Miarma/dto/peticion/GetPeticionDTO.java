package com.salesianostriana.dam.Miarma.dto.peticion;


import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetPeticionDTO {

    private Long id;
    private String texto;
    private String destino;
}
