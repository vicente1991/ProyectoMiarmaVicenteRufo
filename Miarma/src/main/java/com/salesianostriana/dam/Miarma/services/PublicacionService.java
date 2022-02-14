package com.salesianostriana.dam.Miarma.services;

import com.salesianostriana.dam.Miarma.dto.publicacion.CreatePublicacionDTO;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicacionService {
    Publicacion save(CreatePublicacionDTO createPublicacionDTO, MultipartFile file);
    List<Publicacion> findAll();
}
