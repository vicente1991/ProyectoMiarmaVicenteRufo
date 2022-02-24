package com.salesianostriana.dam.Miarma.users.dto;

import com.salesianostriana.dam.Miarma.dto.publicacion.GetPublicacion2;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
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
                .visibilidadUsuario(user.getVisibilidad().name())
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

    public GetUserDtoPost UserEntityToGetUserDtoPosts(Optional<UserEntity> user, List<Publicacion> lista){

        return GetUserDtoPost.builder()
                .id(user.get().getId())
                .nombre(user.get().getNombre())
                .apellidos(user.get().getApellidos())
                .nick(user.get().getNick())
                .fechaNacimiento(user.get().getFechaNacimiento())
                .email(user.get().getEmail())
                .avatar(user.get().getAvatar())
                .userRoles(user.get().getVisibilidad().name())
                .followers(user.get().getSeguidor().stream().map(p -> p.getNick()).toList())
                .publicaciones(lista.stream().map(p -> new GetPublicacion2(p.getId(), p.getTitulo(), p.getTexto(), p.getImagen(), p.getFechaPublicacion(), p.getEstadoPublicacion())).toList())
                .peticiones(user.get().getPublicaciones().size())
                .build();
    }
}
