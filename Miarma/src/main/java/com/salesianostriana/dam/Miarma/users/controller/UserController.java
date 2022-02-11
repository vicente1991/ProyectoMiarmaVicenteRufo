package com.salesianostriana.dam.Miarma.users.controller;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.CreateUserDto;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.GetUserDto;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.UserDtoConverter;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/register")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/user")
    public ResponseEntity<GetUserDto> nuevoPropietario(@RequestBody CreateUserDto createUserDto){
        UserEntity propietario = userEntityService.savePropietario(createUserDto);
        if (propietario == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(propietario));
        }
    }
    @PostMapping("/gestor")
    public ResponseEntity<GetUserDto> nuevoGestor(@RequestBody CreateUserDto createUserDto){
        UserEntity gestor = userEntityService.saveGestor(createUserDto);
        if (gestor == null){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(gestor));
        }
    }
    @PostMapping("/admin")
    public ResponseEntity<GetUserDto> nuevoAdmin(@RequestBody CreateUserDto createUserDto){
        UserEntity admin = userEntityService.saveAdmin(createUserDto);
        if (admin == null){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(admin));
        }
    }

}
