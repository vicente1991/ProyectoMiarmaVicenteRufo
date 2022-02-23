package com.salesianostriana.dam.Miarma.exception;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public class SingleEntityNotFoundExceptionUUID extends EntityNotFoundException {

    public SingleEntityNotFoundExceptionUUID(UUID uuid, Class clase) {
        super(String.format("No se puede encontrar una entidad del tipo %s con id: " + uuid.toString().replace("-", ""), clase.getName()));
    }

}
