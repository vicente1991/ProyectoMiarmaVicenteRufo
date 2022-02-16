package com.salesianostriana.dam.Miarma.dto.peticion;

import com.salesianostriana.dam.Miarma.model.Peticion;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeticionConverterDTO {

    public GetPeticionDTO PeticionToGetPeticionDto(Peticion p){

        return GetPeticionDTO.builder()
                .id(p.getId())
                .texto(p.getPeticion())
                .destino(p.getDestino().getNick())
                .build();

    }

    public Peticion createPeticionDtoToPeticion(CreatePeticionDTO createPeticionDto, UserEntity user, UserEntity user2){

        return Peticion.builder()
                .peticion(createPeticionDto.getTexto() + user2.getNick())
                .destino(user)
                .destino(user2)
                .build();

    }
}
