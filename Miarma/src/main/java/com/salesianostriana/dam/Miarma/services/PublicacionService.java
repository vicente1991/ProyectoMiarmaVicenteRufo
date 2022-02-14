package com.salesianostriana.dam.Miarma.services;

import com.salesianostriana.dam.Miarma.dto.publicacion.CreatePublicacionDTO;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicacionService {
    Publicacion create(CreatePublicacionDTO createPublicacionDTO, MultipartFile file, UserEntity user) throws Exception;
    List<Publicacion> findAll();

}
