package com.salesianostriana.dam.Miarma.users.repos;

import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String nombre);

    Optional<UserEntity> findById(UUID uuid);

    UserEntity findByNick(String nick);

    UserEntity findBySeguidorContains(UserEntity user);
}
