package com.salesianostriana.dam.Miarma.users.dto;

import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto UserEntityToGetUserDto(UserEntity user){
        return GetUserDto.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .nick(user.getNick())
                .fechaNacimiento(user.getFechaNacimiento())
                .numPublicaciones(user.getPublicaciones())
                .numSeguidores(user.getSeguidores().size())
                .numSiguiendo(user.getSiguiendo().size())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }
}
