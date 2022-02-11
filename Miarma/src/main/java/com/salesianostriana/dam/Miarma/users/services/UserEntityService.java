package com.salesianostriana.dam.Miarma.users.services;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Inmobiliaria;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.InmobiliariaService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.base.BaseService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.CreateUserDto;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserRoles;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final InmobiliariaService inmobiliariaService;

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


    public UserEntity saveAdmin(CreateUserDto userDto){
        if (userDto.getPassword().equalsIgnoreCase(userDto.getPassword2())){
            UserEntity userEntity = UserEntity.builder()
                    .nombre(userDto.getNombre())
                    .apellidos(userDto.getApellidos())
                    .direccion(userDto.getDireccion())
                    .email(userDto.getEmail())
                    .telefono(userDto.getTelefono())
                    .avatar(userDto.getAvatar())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .userRoles(UserRoles.ADMIN)
                    .build();
            return save(userEntity);
        }
        else {
            return null;
        }
    }
    public UserEntity savePropietario(CreateUserDto userDto){
        if (userDto.getPassword().equalsIgnoreCase(userDto.getPassword2())){
            UserEntity userEntity = UserEntity.builder()
                    .nombre(userDto.getNombre())
                    .apellidos(userDto.getApellidos())
                    .direccion(userDto.getDireccion())
                    .email(userDto.getEmail())
                    .telefono(userDto.getTelefono())
                    .avatar(userDto.getAvatar())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .userRoles(UserRoles.PROPIETARIO)
                    .build();
            return save(userEntity);
        }
        else {
            return null;
        }
    }
    public UserEntity saveGestor(CreateUserDto userDto){
        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(userDto.getIdInmobiliaria());
        if (userDto.getPassword().equalsIgnoreCase(userDto.getPassword2()) && inmobiliaria.isPresent()){
            UserEntity userEntity = UserEntity.builder()
                    .nombre(userDto.getNombre())
                    .apellidos(userDto.getApellidos())
                    .direccion(userDto.getDireccion())
                    .email(userDto.getEmail())
                    .telefono(userDto.getTelefono())
                    .avatar(userDto.getAvatar())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .userRoles(UserRoles.GESTOR)
                    .inmobiliaria(inmobiliaria.get())
                    .build();
            userEntity.addToInmobiliaria(inmobiliaria.get());
            return save(userEntity);
        }
        else {
            return null;
        }
    }
}
