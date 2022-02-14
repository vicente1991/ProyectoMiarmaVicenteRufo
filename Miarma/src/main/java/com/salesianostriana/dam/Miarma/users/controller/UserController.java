package com.salesianostriana.dam.Miarma.users.controller;

import com.salesianostriana.dam.Miarma.users.dto.CreateUserDto;
import com.salesianostriana.dam.Miarma.users.dto.GetUserDto;
import com.salesianostriana.dam.Miarma.users.dto.UserDtoConverter;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("auth/register")
    public ResponseEntity<GetUserDto> nuevoUser(@RequestPart("user") CreateUserDto createUserDto, @RequestPart("file")MultipartFile file) throws Exception {
        UserEntity usuario = userEntityService.saveUser(createUserDto, file);
        if (usuario == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(usuario));
        }
    }

}
