package com.api.crud.controllers;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
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
