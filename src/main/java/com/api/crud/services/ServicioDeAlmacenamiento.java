package com.api.crud.services;

import com.api.crud.models.Detector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ServicioDeAlmacenamiento {

    @Autowired
    Detector detector;

    // Define la ruta donde se guardarán las imagenes
    private final String DIRECTORIO_DE_IMAGENES = "src/main/resources/imagenes/";

    public boolean guardarArchivo(MultipartFile nuevaImagen, Integer id) throws IOException {
        if(!nuevaImagen.isEmpty()){
            try {
                // Analizar imagen
                return analizarImagen(guardarEnLocal(nuevaImagen, 0), id);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    private String guardarEnLocal(MultipartFile nuevaImagen, Integer id) throws IOException {
        byte[] bytes = nuevaImagen.getBytes();
        Path path = Paths.get(DIRECTORIO_DE_IMAGENES + id + ".jpg");
        Files.write(path, bytes);
        return path.toUri().getPath();
    }

    private boolean analizarImagen(String nuevaImagen, Integer id){
        // Ruta actual del archivo
        String nombreActual = DIRECTORIO_DE_IMAGENES + "0.jpg";


        // Nuevo nombre del archivo
        String nuevoNombre = id + ".jpg";

        // Crear un objeto File con la ruta actual del archivo
        Path rutaActual = Paths.get(nombreActual);
        // Crear un objeto File con la ruta del nuevo archivo
        Path nuevoArchivo = Paths.get(rutaActual.getParent().toString(), nuevoNombre);


        if(detector.containsFace(nombreActual)){

            try {
                Files.move(rutaActual, nuevoArchivo, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        try {
            Files.delete(Paths.get(nombreActual));
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
        return false;
    }

}
