package com.salesianostriana.dam.Miarma.services.impl;



import com.salesianostriana.dam.Miarma.config.StorageProperties;
import com.salesianostriana.dam.Miarma.exception.FileNotFoundException;
import com.salesianostriana.dam.Miarma.exception.StorageException;
import com.salesianostriana.dam.Miarma.services.StorageService;
import com.salesianostriana.dam.Miarma.utils.MediaTypeUrlResource;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;


    public static BufferedImage simpleImageResizer(BufferedImage bufferedImage, int targetWitdth){
        return Scalr.resize(bufferedImage, targetWitdth);
    }


    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }


    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }



    @Override
    public String storeOriginal(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            newFilename = filename;
            while(Files.exists(rootLocation.resolve(newFilename))) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("."+extension,"");

                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                newFilename = name + "_" + suffix + "." + extension;

            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }



        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, ex);
        }

        return newFilename;

    }

    @Override
    public String storePublication(MultipartFile file) throws Exception {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            newFilename = calculateNewFilename(filename);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, ex);
        }
        return newFilename;

    }

    @Override
    public String store(byte[] file, String filename, String contentype) {

        String newFilename= StringUtils.cleanPath(filename);
        if(file.length==0)
            throw new StorageException("El fichero esta vacio");

        newFilename= calculateNewFilename(newFilename);

        try{
            Files.write(rootLocation.resolve(newFilename),file);
        }catch (IOException ex){
            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, ex);

        }
        return null;
    }

    private String calculateNewFilename(String filename){
        String newFilename= filename;
        while(Files.exists(rootLocation.resolve(newFilename))) {
            String extension = StringUtils.getFilenameExtension(newFilename);
            String name = newFilename.replace("."+extension,"");

            String suffix = Long.toString(System.currentTimeMillis());
            suffix = suffix.substring(suffix.length()-6);

            newFilename = name + "_" + suffix + "." + extension;

        }
        return filename;
    }


    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Error al leer los ficheros almacenados", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {

        try {
            Path file = load(filename);
            MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "No se ha podido leer la imagen: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("No se ha podido leer la imagen: " + filename, e);
        }
    }

    @Override
    public void deleteFile(Path filename) throws Exception {
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public BufferedImage escaler(BufferedImage imagen, int width) throws Exception{
        return Scalr.resize(imagen,width);
    }


}
