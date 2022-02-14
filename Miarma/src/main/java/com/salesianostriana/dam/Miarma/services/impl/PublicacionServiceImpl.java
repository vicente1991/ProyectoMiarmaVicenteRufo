package com.salesianostriana.dam.Miarma.services.impl;

import com.salesianostriana.dam.Miarma.dto.publicacion.CreatePublicacionDTO;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.repository.PublicacionRepository;
import com.salesianostriana.dam.Miarma.services.PublicacionService;
import com.salesianostriana.dam.Miarma.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final StorageService storageService;


    @Override
    public Publicacion save(CreatePublicacionDTO createPublicacionDTO, MultipartFile file) {
        String filename= storageService.storeOriginal(file);

        String uri= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(filename)
                .toUriString();

        return publicacionRepository.save(Publicacion.builder()
                        .titulo(createPublicacionDTO.getTitulo())
                        .texto(createPublicacionDTO.getTexto())
                        //.estadoPublicacion(createPublicacionDTO.getEstadoPubli())
                        .imagen(createPublicacionDTO.getImagen())
                .build());
    }

    @Override
    public List<Publicacion> findAll() {
        return publicacionRepository.findAll();
    }
}
