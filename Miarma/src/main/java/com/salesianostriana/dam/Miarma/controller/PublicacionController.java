package com.salesianostriana.dam.Miarma.controller;


import com.salesianostriana.dam.Miarma.dto.publicacion.CreatePublicacionDTO;
import com.salesianostriana.dam.Miarma.dto.publicacion.GetPublicacionDTO;
import com.salesianostriana.dam.Miarma.dto.publicacion.PublicacionConverterDTO;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.repository.PublicacionRepository;
import com.salesianostriana.dam.Miarma.services.impl.PublicacionServiceImpl;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<GetPublicacionDTO> create(@RequestPart("file") MultipartFile file,
                                                    @RequestPart("newPublicacion") CreatePublicacionDTO newPublicacion,
                                                    @AuthenticationPrincipal UserEntity user) throws Exception {
        Publicacion pub = publicacionService.create(newPublicacion, file, user);

        GetPublicacionDTO publicacionDTO = dto.PublicacionToGetPublicacionDto(pub);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(publicacionDTO);
    }

    @GetMapping("/")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(publicacionService.findAll());
    }


    /*@GetMapping("/public")
    public ResponseEntity<List<GetPublicacionDTO>> findAllPublicaciones() {

        if (publicacionService.findAllPublicacionesPublic().isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetPublicacionDTO> list = publicacionService.findAllPublicacionesPublic().stream()
                    .map(dto::createPublicacionDtoToPublicacion)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }

    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Optional<GetPublicacionDTO>> updatePublicacion(@PathVariable Long id, @RequestPart("editPost") CreatePublicacionDTO createPublicacionDto, @RequestPart("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(publicacionService.actualizarPubli(id, createPublicacionDto, file));

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
        return ResponseEntity.ok().body(publicacionService.findAllPubliLog(user));
    }

}
