package com.salesianostriana.dam.Miarma.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String storeAvatar(MultipartFile file) throws IOException, Exception;

    String storeOriginal(MultipartFile file);

    String storePublication(MultipartFile file) throws  Exception;

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteFile(String filename);

    void deleteAll();

}
