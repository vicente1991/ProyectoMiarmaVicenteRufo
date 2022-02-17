package com.salesianostriana.dam.Miarma.services.impl;

import com.salesianostriana.dam.Miarma.dto.publicacion.CreatePublicacionDTO;
import com.salesianostriana.dam.Miarma.dto.publicacion.GetPublicacionDTO;
import com.salesianostriana.dam.Miarma.dto.publicacion.PublicacionConverterDTO;
import com.salesianostriana.dam.Miarma.model.EstadoPublicacion;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import com.salesianostriana.dam.Miarma.repository.PublicacionRepository;
import com.salesianostriana.dam.Miarma.services.PublicacionService;
import com.salesianostriana.dam.Miarma.services.StorageService;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final StorageService storageService;
    private final PublicacionConverterDTO dto;
    private final UserEntityRepository userEntityRepository;


    @Override
    public Publicacion create(CreatePublicacionDTO createPublicacionDTO, MultipartFile file, UserEntity user) throws Exception {
        String filename= storageService.storePublication(file);

        String uri= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return publicacionRepository.save(dto.createPublicacionDtoToPublicacion(createPublicacionDTO,uri,user));
    }

    @Override
    public List<Publicacion> findAll() {
        return publicacionRepository.findAll();
    }


    public List<Publicacion> findAllPublicacionesPublic(){return publicacionRepository.findByEstadoPublicacion(EstadoPublicacion.PUBLICO);}


    public Optional<GetPublicacionDTO> actualizarPubli (Long id, CreatePublicacionDTO p, MultipartFile file) throws Exception {
        if (file.isEmpty()){

            Optional<Publicacion> data = publicacionRepository.findById(id);

            return data.map(m -> {
                m.setTitulo(p.getTitulo());
                m.setTexto(p.getTexto());
                m.setEstadoPublicacion(p.getEstadoPubli());
                m.setFechaPublicacion(LocalDate.from(Instant.now()));
                m.setImagen(m.getImagen());
                publicacionRepository.save(m);
                return dto.PublicacionToGetPublicacionDto(m);
            });

        }else{

            Optional<Publicacion> data = publicacionRepository.findById(id);
            String name = StringUtils.cleanPath(String.valueOf(data.get().getImagen())).replace("http://localhost:8080/download/", "");
            Path pa = storageService.load(name);
            String filename = StringUtils.cleanPath(String.valueOf(pa)).replace("http://localhost:8080/download/", "");;
            Path path = Paths.get(filename);
            storageService.deleteFile(path);
            String original = storageService.storeOriginal(file);
            String newFilename = storageService.storePublication(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(newFilename)
                    .toUriString();

            return data.map(m -> {
                m.setTitulo(p.getTitulo());
                m.setTexto(p.getTexto());
                m.setEstadoPublicacion(p.getEstadoPubli());
                m.setFechaPublicacion(LocalDate.from(Instant.now()));
                m.setImagen(uri);
                publicacionRepository.save(m);
                return dto.PublicacionToGetPublicacionDto(m);
            });
        }
    }

    public void deletePublicacion(Long id) throws Exception {
        Optional<Publicacion> data = publicacionRepository.findById(id);
        String name = StringUtils.cleanPath(String.valueOf(data.get().getImagen())).replace("http://localhost:8080/download", "");
        Path pa = storageService.load(name);
        String filename = StringUtils.cleanPath(String.valueOf(pa)).replace("http://localhost:8080/download", "");
        Path path = Paths.get(filename);
        storageService.deleteFile(path);
        publicacionRepository.deleteById(id);
    }

    public List<GetPublicacionDTO> findAllPubliLog(UserEntity user){

        List<Publicacion> list= publicacionRepository.findByUser(user);
        List<GetPublicacionDTO> listaDto= list.stream().map(p-> new GetPublicacionDTO(p.getId(),
                p.getTitulo(), p.getTexto(),p.getImagen(), p.getFechaPublicacion(), p.getEstadoPublicacion(), p.getUser().getNick())).toList();
        return listaDto;
    }

    public List<GetPublicacionDTO> PostListGraph(UserEntity user){
        List<Publicacion> publi= publicacionRepository.findAll();
        List<Publicacion> publiList= publicacionRepository.findByUserId(user.getId());
        if(publi.isEmpty() && publiList.isEmpty()){
            return Collections.EMPTY_LIST;
        }else{
            return publiList.stream().map(dto::PublicacionToGetPublicacionDto).collect(Collectors.toList());
        }
    }


    public GetPublicacionDTO findOnePubli(Long id, UserEntity user){

        Optional<Publicacion> p = publicacionRepository.findById(id);

        UserEntity s = userEntityRepository.findBySeguidorContains(user);

        if (p.get().getEstadoPublicacion().equals(EstadoPublicacion.PUBLICO) || p.get().getUser().equals(s)){

            return dto.PublicacionToGetPublicacionDto(p.get());

        }else{
            return null;
        }

    }

    public List<GetPublicacionDTO> PostListToGetPubli(String user){
        List<Publicacion> publi= publicacionRepository.findAll();
        List<Publicacion> publiList= publicacionRepository.findAllByUserNick(user);
        if(publi.isEmpty() && publiList.isEmpty()){
            return Collections.EMPTY_LIST;
        }else{
            return publiList.stream().map(dto::PublicacionToGetPublicacionDto).collect(Collectors.toList());
        }
    }

    public List<GetPublicacionDTO> findAllPublicationsOfUser(String nick, UserEntity user){

        List<Publicacion> publiList = publicacionRepository.findAll();
        List<Publicacion> publiList1 = publicacionRepository.findOneByUserNick(nick);
        List<Publicacion> publiList2 = publicacionRepository.findPubli(EstadoPublicacion.PUBLICO, nick);
        UserEntity u = userEntityRepository.findByNick(nick);
        UserEntity s = userEntityRepository.findBySeguidorContains(user);
        if(publiList.isEmpty() && publiList.isEmpty()){
            return Collections.EMPTY_LIST;
        }else if (!u.equals(s)){
            return publiList2.stream().map(dto::PublicacionToGetPublicacionDto).collect(Collectors.toList());

        }else{
            return publiList1.stream().map(dto::PublicacionToGetPublicacionDto).collect(Collectors.toList());
        }

    }


}
