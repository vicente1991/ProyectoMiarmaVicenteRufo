package com.salesianostriana.dam.Miarma.controller;


import com.salesianostriana.dam.Miarma.dto.publicacion.CreatePublicacionDTO;
import com.salesianostriana.dam.Miarma.services.PublicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionService publicacionService;

    @PostMapping("/")
    public ResponseEntity<?> create (@RequestPart("file")MultipartFile file,
                                     @RequestPart("newPublicacion")CreatePublicacionDTO newPublicacion){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(publicacionService.save(newPublicacion,file));
    }

    @GetMapping("/")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(publicacionService.findAll());
    }
}
