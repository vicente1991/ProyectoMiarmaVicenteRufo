package com.salesianostriana.dam.Miarma.dto.publicacion;

import com.salesianostriana.dam.Miarma.model.EstadoPublicacion;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class PublicacionConverterDTO {

    public Publicacion createPublicacionDtoToPublicacion(CreatePublicacionDTO p, String uri){

        return Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .imagen(uri)
                .fechaPublicacion(Date.from(Instant.now()))
                .estadoPublicacion(p.getEstadoPubli().toUpperCase().equalsIgnoreCase(EstadoPublicacion.PUBLICO.toString()) ? EstadoPublicacion.PUBLICO : EstadoPublicacion.PRIVADO)
                .build();
    }

    public GetPublicacionDTO PublicacionToGetPublicacionDto(Publicacion p){
        return GetPublicacionDTO.builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .file(p.getImagen())
                .fechaPublicacion(p.getFechaPublicacion())
                .estadoPublicacion(p.getEstadoPublicacion())
                .build();
    }


}
