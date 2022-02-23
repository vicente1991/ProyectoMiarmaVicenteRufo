package com.salesianostriana.dam.Miarma.dto.publicacion;

import com.salesianostriana.dam.Miarma.model.EstadoPublicacion;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.users.dto.UserDtoConverter;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class PublicacionConverterDTO {

    @Autowired
    private final UserDtoConverter userDtoConverter;

    public Publicacion createPublicacionDtoToPublicacion(CreatePublicacionDTO p, String uri, UserEntity user){

        return Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .imagen(uri)
                .fechaPublicacion(LocalDate.now())
                .estadoPublicacion(p.getEstadoPubli())
                .user(user)
                .build();
    }


    public GetPublicacionDTO PublicacionToGetPublicacionDto(Publicacion p){
        return GetPublicacionDTO.builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .file(p.getImagen())
                .fechaPublicacion(p.getFechaPublicacion())
                .visibilidad(p.getEstadoPublicacion())
                .user(userDtoConverter.UserEntityToGetUserDto(p.getUser()))
                .build();
    }


}
