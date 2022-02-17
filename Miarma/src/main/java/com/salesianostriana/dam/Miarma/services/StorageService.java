package com.salesianostriana.dam.Miarma.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String storeOriginal(MultipartFile file);

    String storePublication(MultipartFile file) throws  Exception;

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteFile(Path filename)throws Exception;

    void deleteAll();

    BufferedImage escaler(BufferedImage imagen, int width) throws Exception;

}
