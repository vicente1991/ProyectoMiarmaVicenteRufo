package com.salesianostriana.dam.Miarma.exception;


public class PublicacionException extends StorageException{
    public PublicacionException(String message, Exception e) {
        super(message, e);
    }

    public PublicacionException(String message) {
        super(message);
    }
}
