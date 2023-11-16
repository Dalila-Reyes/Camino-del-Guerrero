package com.api.crud.controllers;

import com.api.crud.models.ClasificadorModel;
import com.api.crud.models.Detector;
import com.api.crud.models.UserModel;
import com.api.crud.repositories.ClasificadorRepository;
import com.api.crud.services.ServicioDeAlmacenamiento;

import com.api.crud.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5173/", "http://127.0.0.1:5173/",
                        "https://dalila-reyes.github.io/Frontend/",
                        "https://dalila-reyes.github.io/",
                        "https://dalila-reyes.github.io/ListaAlumnos",
                        "https://dalila-reyes.github.io/Register",
                        "https://dalila-reyes.github.io/Login",
                        "https://dalila-reyes.github.io/assets/index-db0db791.js",
                        "https://dalila-reyes.github.io/assets/index-83a5a68a.css",
                        "https://dalila-reyes.github.io/vite.svg"})

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    ServicioDeAlmacenamiento servicioDeAlmacenamiento;

    @Autowired
    ClasificadorRepository clasificadorRepository;



    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestBody UserModel user) {

        UserModel userDB = userService.findByemail(user.getEmail());
        // Realiza la autenticación del usuario aquí (por ejemplo, verifica las credenciales)
        if (userDB != null
                &&
                userDB.getContraseña().equals(user.getContraseña())) {

            // Usuario autenticado correctamente
            // Genera un token de sesión y envíalo al frontend
            // String token = "123456789"; // Debe ser único
            return ResponseEntity.ok(userDB);
        } else {
            // Autenticación fallida
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping
    public ArrayList<UserModel> getUsers(){
        return this.userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserModel user){
        this.userService.saveUser(user);

        String mensaje = "{ \"mensaje\": \"Datos actualizados con éxito\"}";
        return ResponseEntity.ok().body(mensaje);
    }




    @GetMapping(path="/{id}")
    public Optional<UserModel> getUserById(@PathVariable Integer id){
        return this.userService.getById(id);
    }

    @PostMapping(path = "/clasificador", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> setClasificador(
            @RequestPart("clasificador") MultipartFile clasificador) throws IOException {

        byte[] bytes = clasificador.getBytes();
        ClasificadorModel nuevoClasificador = new ClasificadorModel();
        nuevoClasificador.setClasificador(bytes);
        clasificadorRepository.save(nuevoClasificador);


        String mensaje = "{\"mensaje\": \"Datos actualizados con éxito\"}";
        return ResponseEntity.ok().body(mensaje);
    }


    @PostMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserById(
            @PathVariable Integer id,
            @RequestPart("data") UserModel user,
            @RequestPart("photo") MultipartFile photo) throws IOException {


        if(photo != null){
            // Guardar la imagen en tu servidor y obtener la ruta
            //String imagePath = saveImage(photo, id);
            byte[] imagebyte = saveImageByte(photo);
            // Actualizar el UserModel con la ruta de la imagen
            if(imagebyte != null){
                user.setProfileImage(imagebyte);
            }
            else{
                userService.saveUser(user);
                String mensaje = "{\"status\": \"Error\",\"mensaje\": \"Elige una foto con tu rostro\"}";
                return ResponseEntity.ok().body(mensaje);
            }
        }

        userService.saveUser(user);
        String mensaje = "{\"mensaje\": \"Datos actualizados con éxito\"}";
        return ResponseEntity.ok().body(mensaje);

    }

    public byte[] saveImageByte(MultipartFile image){
        return servicioDeAlmacenamiento.guardarArchivoByte(image);
    }

    // Método para guardar la imagen y obtener la ruta
    private String saveImage(MultipartFile image, Integer id) throws IOException {
        try {
            if(servicioDeAlmacenamiento.guardarArchivo(image, id)){
                return "imagenes/" + id + ".jpg";
            }else{
                return "";
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return "";
    }


    @DeleteMapping(path  ="/{id}")
    public  String deleteById(@PathVariable("id") Integer id) {
        boolean ok = this.userService.deleteUser(id);

        if(ok){
            return "User whith id" + id + "deleted¡ ";

        }else{
            return "Error, we have a problem";
        }
    }

    @GetMapping("/getPhoto/{id}")
    public ResponseEntity<Resource> getImagen(@PathVariable Integer id) {

        byte[] imagePath = userService.getById(id).get().getProfileImage();

        try {
            //Resource resource = new ClassPathResource(imagePath);
            //Resource resource = new UrlResource("file:" + "src/main/resources/" + imagePath);
            ByteArrayResource resource = new ByteArrayResource(imagePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            // Manejar excepciones
            return ResponseEntity.notFound().build();
        }
    }


}
