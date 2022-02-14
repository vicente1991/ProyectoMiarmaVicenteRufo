package com.salesianostriana.dam.Miarma.dto.publicacion;
import javax.validation.constraints.NotBlank;
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

    private String estadoPubli;

    @NotBlank
    private String imagen;
}
