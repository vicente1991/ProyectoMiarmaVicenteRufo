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
                .direccion(user.getDireccion())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .avatar(user.getAvatar())
                .userRoles(user.getUserRoles().name())
                .build();
    }
}
