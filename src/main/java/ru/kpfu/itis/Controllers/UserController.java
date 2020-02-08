package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.UserForm;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.UserRepository;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;



    @GetMapping
    public List<User> getUsers(){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findAll();
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<User> getUser(@PathVariable("user-id") Long user_id){
       return ResponseEntity.ok(userRepository.findOne(user_id));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody UserForm userForm){
        userService.signUp(userForm);
        return ResponseEntity.ok().build();
    }
}