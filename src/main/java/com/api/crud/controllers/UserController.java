package com.api.crud.controllers;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserModel user) {

        UserModel userDB = userService.findByemail(user.getEmail());
        // Realiza la autenticación del usuario aquí (por ejemplo, verifica las credenciales)
        if (userDB != null
                &&
                userDB.getContraseña().equals(user.getContraseña())) {

            // Usuario autenticado correctamente
            // Genera un token de sesión y envíalo al frontend
            String token = "123456789"; // Debe ser único
            return ResponseEntity.ok(token);
        } else {
            // Autenticación fallida
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autenticación fallida");
        }
    }

    @GetMapping
    public ArrayList<UserModel> getUsers(){

        return this.userService.getUsers();
    }

    @PostMapping
    public UserModel saveUser(@RequestBody UserModel user){

        return this.userService.saveUser(user);
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
