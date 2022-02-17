package com.salesianostriana.dam.Miarma.users.services;

import com.salesianostriana.dam.Miarma.dto.peticion.CreatePeticionDTO;
import com.salesianostriana.dam.Miarma.dto.peticion.PeticionConverterDTO;
import com.salesianostriana.dam.Miarma.exception.UsuarioException;
import com.salesianostriana.dam.Miarma.model.Peticion;
import com.salesianostriana.dam.Miarma.repository.PeticionRepository;
import com.salesianostriana.dam.Miarma.services.StorageService;
import com.salesianostriana.dam.Miarma.services.base.BaseService;
import com.salesianostriana.dam.Miarma.services.impl.PeticionService;
import com.salesianostriana.dam.Miarma.users.dto.CreateUserDto;
import com.salesianostriana.dam.Miarma.users.dto.GetUserDTOFollowers;
import com.salesianostriana.dam.Miarma.users.dto.GetUserDto;
import com.salesianostriana.dam.Miarma.users.dto.UserDtoConverter;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.model.UserRoles;
import com.salesianostriana.dam.Miarma.users.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final PeticionService peticionService;
    private final PeticionConverterDTO peticionConverterDTO;
    private final PeticionRepository peticionRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserDtoConverter userDtoConverter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " no encontrado"));
    }
    public UserEntity loadUserById(UUID uuid) throws UsernameNotFoundException{
        return this.repository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException(uuid + " no encontrado"));
    }



    public UserEntity saveUser(CreateUserDto userDto, MultipartFile file) throws Exception {

        String filename = storageService.storePublication(file);

        String ext = StringUtils.getFilenameExtension(filename);

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        BufferedImage escaledImage = storageService.escaler(originalImage,128);

        OutputStream outputStream = Files.newOutputStream(storageService.load(filename));

        ImageIO.write(escaledImage,ext,outputStream);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        if (userDto.getPassword().equalsIgnoreCase(userDto.getPassword2())){
            UserEntity userEntity = UserEntity.builder()
                    .nick(userDto.getNick())
                    .nombre(userDto.getNombre())
                    .apellidos(userDto.getApellidos())
                    .email(userDto.getEmail())
                    .visibilidad(userDto.getVisibilidad())
                    .fechaNacimiento(userDto.getFechaNacimiento())
                    .avatar(uri)
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();
            return save(userEntity);
        }
        else {
            return null;
        }
    }

    public List<Peticion> findAllPet (){

        return peticionService.findAll();
    }

    public Optional<GetUserDto> actualizarPerfil(UserEntity user, CreateUserDto u, MultipartFile file) throws Exception {
        if (file.isEmpty()){

            Optional<UserEntity> data = userEntityRepository.findById(user.getId());

            return data.map(m -> {
                m.setNombre(u.getNombre());
                m.setApellidos(user.getApellidos());
                m.setNick(u.getNick());
                m.setVisibilidad(u.getVisibilidad());
                m.setAvatar(m.getAvatar());
                m.setEmail(u.getEmail());
                userEntityRepository.save(m);
                return userDtoConverter.UserEntityToGetUserDto(m);
            });

        }else{

            Optional<UserEntity> data = userEntityRepository.findById(user.getId());
            String name = StringUtils.cleanPath(String.valueOf(data.get().getAvatar())).replace("http://localhost:8080/download/", "");
            Path p = storageService.load(name);
            String filename = StringUtils.cleanPath(String.valueOf(p)).replace("http://localhost:8080/download/", "");
            Path pa = Paths.get(filename);
            storageService.deleteFile(pa);
            String avatar = storageService.storePublication(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(avatar)
                    .toUriString();

            return data.map(m -> {
                m.setNombre(u.getNombre());
                m.setApellidos(user.getApellidos());
                m.setNick(u.getNick());
                m.setVisibilidad(u.getVisibilidad());
                m.setAvatar(uri);
                m.setEmail(u.getEmail());
                userEntityRepository.save(m);
                return userDtoConverter.UserEntityToGetUserDto(m);
            });

        }
    }

    public Peticion sendPeticion (String nick, CreatePeticionDTO createPeticionDto, UserEntity user){
        UserEntity usuario= userEntityRepository.findByNick(nick);
        if(usuario==null || usuario.getNick().equals(user.getNick())){
            throw new UsuarioException("No puedes enviarte peticiones a ti mismo");
        }else{
            Peticion peticion = Peticion.builder()
                    .peticion(createPeticionDto.getTexto() + user.getNick())
                    .destino(usuario)
                    .build();
            usuario.addPeticion(peticion);
            peticionRepository.save(peticion);
            return peticion;
        }
    }

    public void aceptarPeticion(Long id, UserEntity user){

        Optional<Peticion> peticion = peticionRepository.findById(id);
        user.addSeguidor(peticion.get().getDestino());
        peticion.get().nullearDestinatarios();
        peticionRepository.deleteById(id);
    }

    public void rechazarPeticion(Long id){
        Optional<Peticion> peticionSeguimiento = peticionRepository.findById(id);
        peticionSeguimiento.get().nullearDestinatarios();

        peticionRepository.save(peticionSeguimiento.get());
        peticionRepository.deleteById(id);
    }

    public GetUserDTOFollowers verPerfilDeUsuario (UUID id){
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        return userDtoConverter.UserEntityToGetUserDtoWithFollowers(userEntity);
    }

}
