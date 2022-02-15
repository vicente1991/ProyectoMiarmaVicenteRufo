package com.salesianostriana.dam.Miarma.dto.publicacion;
import javax.validation.constraints.NotBlank;

import com.salesianostriana.dam.Miarma.model.EstadoPublicacion;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePublicacionDTO {

    @NotNull
    @NotBlank
    private String titulo;

    private String texto;

    private EstadoPublicacion estadoPubli;

    @NotBlank
    private String imagen;
}
