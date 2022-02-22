package com.salesianostriana.dam.Miarma.images;

import java.awt.image.ImagingOpException;

public class ImageProcessException extends RuntimeException {
    public ImageProcessException(String mensaje, Exception ex) {
        super(mensaje, ex);
    }
}
