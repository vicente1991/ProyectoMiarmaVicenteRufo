package com.salesianostriana.dam.Miarma.users.controller;

import com.salesianostriana.dam.Miarma.dto.peticion.CreatePeticionDTO;
import com.salesianostriana.dam.Miarma.dto.peticion.GetPeticionDTO;
import com.salesianostriana.dam.Miarma.dto.peticion.PeticionConverterDTO;
import com.salesianostriana.dam.Miarma.model.Peticion;
import com.salesianostriana.dam.Miarma.services.impl.PeticionService;
import com.salesianostriana.dam.Miarma.users.dto.CreateUserDto;
import com.salesianostriana.dam.Miarma.users.dto.GetUserDTOFollowers;
import com.salesianostriana.dam.Miarma.users.dto.GetUserDto;
import com.salesianostriana.dam.Miarma.users.dto.UserDtoConverter;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.model.UserRoles;
import com.salesianostriana.dam.Miarma.users.repos.UserEntityRepository;
import com.salesianostriana.dam.Miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final PeticionService peticionService;
    private final PeticionConverterDTO peticionConverterDTO;
    private final UserEntityRepository userEntityRepository;

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

    @PostMapping("follow/{nick}")
    public ResponseEntity<GetPeticionDTO> realizarfollow (@PathVariable String nick, @RequestPart("peticion") CreatePeticionDTO createPeticionDto, @AuthenticationPrincipal UserEntity user){

        Peticion peticion = userEntityService.sendPeticion(nick, createPeticionDto, user);

        return  ResponseEntity.status(HttpStatus.CREATED).body(peticionConverterDTO.PeticionToGetPeticionDto(peticion));
    }

    @PostMapping("follow/accept/{id}")
    public ResponseEntity<?> acceptPeticion(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){

        if (id.equals(null)){
            throw new NoSuchElementException();
        }else {
            userEntityService.aceptarPeticion(id, userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }

    @PostMapping("follow/decline/{id}")
    public ResponseEntity<?> declinePeticion(@PathVariable Long id){
        if (id.equals(null)){
            throw new NoSuchElementException();
        }else {
            userEntityService.rechazarPeticion(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }

    @GetMapping("profile/{id}")
    public ResponseEntity<GetUserDTOFollowers> verPerfilUsuario(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user){

        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if (userEntity.get().getVisibilidad().equals(UserRoles.PUBLICO) || userEntity.get().getSeguidor().contains(user)){
            GetUserDTOFollowers getUserDtoWithSeguidores = userEntityService.verPerfilDeUsuario(id);
            return ResponseEntity.ok().body(getUserDtoWithSeguidores);
        }else {
            throw new NoSuchElementException();
        }

    }
}
