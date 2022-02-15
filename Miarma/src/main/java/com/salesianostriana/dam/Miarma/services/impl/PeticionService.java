package com.salesianostriana.dam.Miarma.services.impl;

import com.salesianostriana.dam.Miarma.model.Peticion;
import com.salesianostriana.dam.Miarma.repository.PeticionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeticionService {

    private final PeticionRepository peticionRepository;


    public List<Peticion> findAll (){

        return peticionRepository.findAll();

    }
}
