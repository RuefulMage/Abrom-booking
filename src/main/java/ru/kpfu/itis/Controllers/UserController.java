package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.UserForm;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.UserRepository;
import ru.kpfu.itis.Services.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{user-id}")
    public User getUser(@PathVariable("user-id") Long user_id){
       return userRepository.findOne(user_id);
    }

    @PostMapping("users")
    public ResponseEntity<Object> addUser(@RequestBody UserForm userForm){
        userService.signUp(userForm);
        return ResponseEntity.ok().build();
    }
}

