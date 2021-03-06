package com.salesianostriana.dam.Miarma.controller;


import com.salesianostriana.dam.Miarma.dto.publicacion.CreatePublicacionDTO;
import com.salesianostriana.dam.Miarma.dto.publicacion.GetPublicacionDTO;
import com.salesianostriana.dam.Miarma.dto.publicacion.PublicacionConverterDTO;
import com.salesianostriana.dam.Miarma.exception.ListEntityNotFoundException;
import com.salesianostriana.dam.Miarma.exception.PublicacionException;
import com.salesianostriana.dam.Miarma.exception.SingleEntityNotFoundException;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.repository.PublicacionRepository;
import com.salesianostriana.dam.Miarma.services.impl.PublicacionServiceImpl;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionServiceImpl publicacionService;
    private final PublicacionConverterDTO dto;
    private final PublicacionRepository publicacionRepository;

    @PostMapping("/")
    public ResponseEntity<GetPublicacionDTO> createPublicacion(@RequestParam("titulo") String titulo, @RequestParam("texto") String texto, @RequestParam("estadoPublicacion") boolean estadoPublicacion, @RequestPart("file")MultipartFile file, @AuthenticationPrincipal UserEntity user) throws Exception {

        CreatePublicacionDTO createPublicacionDto = CreatePublicacionDTO.builder()
                .titulo(titulo)
                .texto(texto)
                .estadoPubli(estadoPublicacion)
                .build();

        publicacionService.create(createPublicacionDto, file, user);

        GetPublicacionDTO publicacionDto = dto.PublicacionToGetPublicacionDto(publicacionRepository.findByTitulo(createPublicacionDto.getTitulo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionDto);

    }



    @GetMapping("/public")
    public ResponseEntity<List<GetPublicacionDTO>> findAllPublicaciones(){

        if (publicacionService.findAllPublicacionesPublic().isEmpty()) {
            throw new ListEntityNotFoundException(Publicacion.class);
        } else {
            List<GetPublicacionDTO> list = publicacionService.findAllPublicacionesPublic().stream()
                    .map(dto::PublicacionToGetPublicacionDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<GetPublicacionDTO>> updatePublicacion(@PathVariable Long id, @RequestPart("editPost") CreatePublicacionDTO createPublicacionDto, @RequestPart("file") MultipartFile file) throws Exception {
        if(id.equals(null)){
            throw new SingleEntityNotFoundException(id.toString(), Publicacion.class);
        }else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(publicacionService.actualizarPubli(id, createPublicacionDto, file));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePubli(@PathVariable Long id) throws Exception {
        if (id.equals(null)){
            throw new FileNotFoundException();
        }else{

            publicacionService.deletePublicacion(id);

            return ResponseEntity.status(204).build();
        }
    }


    @GetMapping("/me")
    public ResponseEntity<List<GetPublicacionDTO>> findAllPubliLog(@AuthenticationPrincipal UserEntity user){
        List<GetPublicacionDTO> post= publicacionService.findAllPubliLog(user);

        return ResponseEntity.ok().body(publicacionService.PostListGraph(user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetPublicacionDTO> findOnePubli(@PathVariable Long id, @AuthenticationPrincipal UserEntity user){
        GetPublicacionDTO p= publicacionService.findOnePubli(id,user);
        if (p==null){
            throw new PublicacionException("La publicacion es privada");
        }else{
            return ResponseEntity.ok().body(p);
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<GetPublicacionDTO>> listAllPostByNick(@RequestParam(value = "nick") String nick, @AuthenticationPrincipal UserEntity u){
        List<GetPublicacionDTO> publi = publicacionService.findAllPublicationsOfUser(nick,u);
        if(u==null){
            throw new PublicacionException("El nick no existe");
        }else{
            return ResponseEntity.ok().body(publi);
        }
    }
}
