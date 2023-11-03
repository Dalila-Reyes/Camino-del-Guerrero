package com.api.crud.controllers;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5173/", "http://127.0.0.1:5173/"})

public class UserController {

    @Autowired
    private UserService userService;

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
    public UserModel saveUser(@RequestBody UserModel user){
        String profileImage = saveImageToServer(user.getProfileImage());

        return this.userService.saveUser(user);
    }

    private String saveImageToServer(UserModel user) throws IOException {
        // Define la carpeta de destino en el servidor
        String uploadDir = "path/to/image/directory";

        // Genera un nombre único para el archivo, por ejemplo, el ID del usuario
        String uniqueFileName = user.getId() + "_" + user.getProfileImage().getOriginalFilename();

        // Crea el path completo para guardar la imagen
        String imagePath = uploadDir + File.separator + uniqueFileName;

        // Guarda la imagen en el servidor
        Path path = Paths.get(imagePath);
        Files.write(path, user.getProfileImage().getBytes());

        return uniqueFileName;
    }


    @GetMapping(path="/{id}")
    public Optional<UserModel> getUserById(@PathVariable Integer id){
        return this.userService.getById(id);
    }
    @PutMapping
    public  UserModel updateUserById(@RequestBody UserModel request, Integer id ) {
        return this.userService.updateById(request,id);
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
}
