package com.salesianostriana.dam.Miarma.security;

import com.salesianostriana.dam.Miarma.security.dto.JwtUsuarioResponse;
import com.salesianostriana.dam.Miarma.security.dto.LoginDto;
import com.salesianostriana.dam.Miarma.security.jwt.JwtProvider;
import com.salesianostriana.dam.Miarma.users.dto.GetUserDto;
import com.salesianostriana.dam.Miarma.users.dto.UserDtoConverter;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDtoConverter userDtoConverter;
    private final UserEntityService entityService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(convertUserToJwtUsuarioResponse(userEntity, jwt));
    }

    private JwtUsuarioResponse convertUserToJwtUsuarioResponse(UserEntity user, String jwt) {
        return JwtUsuarioResponse.builder()
                .id(user.getId())
                .nick(user.getNick())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .token(jwt)
                .build();
    }
    private GetUserDto convertUserToGetUserDto(UserEntity user, String jwt) {
        return GetUserDto.builder()
                .id(user.getId())
                .apellidos(user.getApellidos())
                .fechaNacimiento(user.getFechaNacimiento())
                .nick(user.getNick())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .visibilidadUsuario(user.getVisibilidad().name())
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> perfil(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(entityService.verPerfilDeUsuario(user.getId()));
    }
}


