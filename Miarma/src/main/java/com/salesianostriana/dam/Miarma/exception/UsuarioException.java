package com.salesianostriana.dam.Miarma.exception;

public class UsuarioException extends StorageException{
    public UsuarioException(String message, Exception e) {
        super(message, e);
    }

    public UsuarioException(String message) {
        super(message);
    }
}
