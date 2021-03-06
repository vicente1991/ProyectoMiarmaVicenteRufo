package com.salesianostriana.dam.Miarma.users.model;

import com.salesianostriana.dam.Miarma.model.Peticion;
import com.salesianostriana.dam.Miarma.model.Publicacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class UserEntity implements UserDetails {

    @Id
    @Column(name = "usuario")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;

    private String nombre;

    private String apellidos;

    private String nick;

    @NaturalId
    @Column(unique = true,updatable = false)
    private String email;

    @DateTimeFormat
    private LocalDate fechaNacimiento;

    private String avatar;

    private UserRoles visibilidad;

    private String password;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Publicacion> publicaciones=new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserEntity> seguidor= new ArrayList<>();

    @OneToMany(mappedBy = "destino")
    @Builder.Default
    private List<Peticion> seguido= new ArrayList<>();


    //Helpers

    public void addPeticion(Peticion p){
        if(this.getSeguido()== null){
            this.setSeguido(new ArrayList<>());
        }

    }
    public void addSeguidor(UserEntity u) {
        this.seguidor = List.of(u);
        u.getSeguidor().add(this);
    }

    public void removeSeguidor(UserEntity u) {
        u.getSeguidor().remove(this);
        this.seguidor = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("usuario"));
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
