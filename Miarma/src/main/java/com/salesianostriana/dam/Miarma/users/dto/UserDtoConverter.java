package com.salesianostriana.dam.Miarma.users.dto;

import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDtoConverter {

    public GetUserDto UserEntityToGetUserDto(UserEntity user){
        return GetUserDto.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .nick(user.getNick())
                .fechaNacimiento(user.getFechaNacimiento())
                .visibilidad(user.getVisibilidad().name())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }

    public GetUserDTOFollowers UserEntityToGetUserDtoWithSeguidores(Optional<UserEntity> user){
        return GetUserDTOFollowers.builder()
                .id(user.get().getId())
                .nombre(user.get().getNombre())
                .apellidos(user.get().getApellidos())
                .nick(user.get().getNick())
                .fechaNacimiento(user.get().getFechaNacimiento())
                .email(user.get().getEmail())
                .avatar(user.get().getAvatar())
                .userRoles(user.get().getVisibilidad().name())
                .seguidores(user.get().getSeguidor().stream().map(p -> p.getNick()).toList())
                .peticiones(user.get().getSeguido().size())
                .build();
    }
}
