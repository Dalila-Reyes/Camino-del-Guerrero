package com.api.crud.services;

import com.api.crud.models.UserModel;
import com.api.crud.repositories.IUserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;

    public ArrayList<UserModel>  getUsers(){

        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel saveUser(UserModel user){

        return userRepository.save(user);
    }
    public Optional <UserModel> getById (Integer id){
        return userRepository.findById(id);
    }

    public  UserModel updateById(UserModel  request ,Integer id){
        UserModel user = userRepository.findById(id).get();

                user.setFirst_name(request.getFirst_name());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());

                        return user;

    }
    public Boolean deleteUser (Integer id){
        try{
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }



}
