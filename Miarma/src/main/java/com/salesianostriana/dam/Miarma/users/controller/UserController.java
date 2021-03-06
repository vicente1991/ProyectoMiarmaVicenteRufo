package com.salesianostriana.dam.Miarma.users.controller;

import com.salesianostriana.dam.Miarma.dto.peticion.CreatePeticionDTO;
import com.salesianostriana.dam.Miarma.dto.peticion.GetPeticionDTO;
import com.salesianostriana.dam.Miarma.dto.peticion.PeticionConverterDTO;
import com.salesianostriana.dam.Miarma.exception.UsuarioException;
import com.salesianostriana.dam.Miarma.model.Peticion;
import com.salesianostriana.dam.Miarma.services.impl.PeticionService;
import com.salesianostriana.dam.Miarma.users.dto.*;
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

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<GetUserDto> nuevoUser(@Valid @RequestParam("nombre") String nombre, @Valid @RequestParam("apellidos") String apellidos, @Valid @RequestParam("nick") String nick, @RequestParam String fechaNacimiento, @Valid @RequestParam("rol") boolean rol, @Valid @RequestParam("password") String password, @Valid @RequestParam("password2") String password2, @Valid @RequestParam("email") String email, @RequestPart("file")MultipartFile file) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        CreateUserDto createUserDto = CreateUserDto.builder()
                .nombre(nombre)
                .apellidos(apellidos)
                .nick(nick)
                .email(email)
                .fechaNacimiento(LocalDate.parse(fechaNacimiento, formatter))
                .visibilidadUsuario(rol)
                .password(password)
                .password2(password2)
                .build();


        UserEntity usuario = userEntityService.saveUser(createUserDto, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(usuario));
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
            throw new UsuarioException("No existe id de la peticion");
        }else {
            userEntityService.aceptarPeticion(id, userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }

    @PostMapping("follow/decline/{id}")
    public ResponseEntity<?> declinePeticion(@PathVariable Long id){
        if (id.equals(null)){
            throw new UsuarioException("No existe id de la peticion");
        }else {
            userEntityService.rechazarPeticion(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }

    @GetMapping("profile/{id}")
    public ResponseEntity<GetUserDtoPost> verPerfilDeUsuario(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user){

        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        UserEntity user1 = userEntityRepository.findBySeguidorContains(user);

        if (userEntity.get().getVisibilidad().equals(UserRoles.PUBLICO) || user1.getNick().equals(userEntity.get().getNick())){

            GetUserDtoPost getUserDtoWithFollowers = userEntityService.verPerfilDeUsuario(id);
            return ResponseEntity.ok().body(getUserDtoWithFollowers);

        }else {
            throw new UsuarioException("El usuario(userEntity) proporcionado por nick tiene su perfil privado y el usuario(@AuthenticationPrincipal user) solicitante no forma parte de sus seguidores");
        }

    }
}
