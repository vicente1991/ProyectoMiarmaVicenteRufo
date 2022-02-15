package com.salesianostriana.dam.Miarma.users.controller;

import com.salesianostriana.dam.Miarma.dto.peticion.GetPeticionDTO;
import com.salesianostriana.dam.Miarma.dto.peticion.PeticionConverterDTO;
import com.salesianostriana.dam.Miarma.services.impl.PeticionService;
import com.salesianostriana.dam.Miarma.users.dto.CreateUserDto;
import com.salesianostriana.dam.Miarma.users.dto.GetUserDto;
import com.salesianostriana.dam.Miarma.users.dto.UserDtoConverter;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final PeticionService peticionService;
    private final PeticionConverterDTO peticionConverterDTO;

    @PostMapping("auth/register")
    public ResponseEntity<GetUserDto> nuevoUser(@RequestPart("user") CreateUserDto createUserDto, @RequestPart("file")MultipartFile file) throws Exception {
        UserEntity usuario = userEntityService.saveUser(createUserDto, file);
        if (usuario == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(usuario));
        }
    }
    @GetMapping("follow/list")
    public ResponseEntity<List<GetPeticionDTO>> listadoPeticiones(){
        if (peticionService.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetPeticionDTO> list = peticionService.findAll().stream()
                    .map(peticionConverterDTO::PeticionToGetPeticionDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }
    }

    @PutMapping("profile/me")
    public ResponseEntity<Optional<GetUserDto>> actualizarPerfil (@AuthenticationPrincipal UserEntity userEntity, @RequestPart("user") CreateUserDto createUserDto, @RequestPart("file")MultipartFile file) throws Exception {

        return ResponseEntity.ok(userEntityService.actualizarPerfil(userEntity, createUserDto, file));
    }
}
