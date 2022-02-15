package com.salesianostriana.dam.Miarma.dto.peticion;


import lombok.*;

import javax.persistence.Lob;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreatePeticionDTO {

    @Lob
    private String texto;
}
