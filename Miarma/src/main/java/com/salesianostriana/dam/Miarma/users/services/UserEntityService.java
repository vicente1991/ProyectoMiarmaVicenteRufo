package com.salesianostriana.dam.Miarma.users.services;

import com.salesianostriana.dam.Miarma.services.StorageService;
import com.salesianostriana.dam.Miarma.services.base.BaseService;
import com.salesianostriana.dam.Miarma.users.dto.CreateUserDto;
import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.model.UserRoles;
import com.salesianostriana.dam.Miarma.users.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " no encontrado"));
    }
    public List<UserEntity> loadUserByRole(UserRoles userRoles) throws UsernameNotFoundException{
        return this.repository.findByUserRoles(userRoles).orElseThrow(() -> new UsernameNotFoundException(userRoles + " no encontrado"));
    }
    public UserEntity loadUserById(UUID uuid) throws UsernameNotFoundException{
        return this.repository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException(uuid + " no encontrado"));
    }



    public UserEntity saveUser(CreateUserDto userDto, MultipartFile file) throws Exception {

        String filename = storageService.storeAvatar(file);

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
                    .fechaNacimiento(userDto.getFechaNacimiento())
                    .avatar(uri)
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .userRoles(userDto.getRol().toUpperCase().equalsIgnoreCase(UserRoles.PUBLICO.name()) ? UserRoles.PUBLICO:UserRoles.PRIVADO)
                    .build();
            return save(userEntity);
        }
        else {
            return null;
        }
    }

}
