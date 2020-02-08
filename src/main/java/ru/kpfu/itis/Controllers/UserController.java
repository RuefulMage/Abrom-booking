package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.UserForm;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.UserRepository;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Services.UserService;
import ru.kpfu.itis.Transfer.UserDTO;
import ru.kpfu.itis.Utils.UsersMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;
    private UsersMapper usersMapper;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, UsersMapper usersMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.usersMapper = usersMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = users.stream().map(user -> usersMapper.toDto(user))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("user-id") Long user_id){
       return ResponseEntity.ok(usersMapper.toDto(userRepository.findOne(user_id)));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserForm userForm){
        userService.signUp(userForm);
    }
}