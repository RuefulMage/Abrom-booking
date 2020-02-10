package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.LoginForm;
import ru.kpfu.itis.Models.Enums.Role;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Services.LoginService;
import ru.kpfu.itis.Services.TokenService;
import ru.kpfu.itis.Services.UserService;
import ru.kpfu.itis.Transfer.TokenDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private LoginService loginService;
    private TokenService tokenService;
    private UserService userService;

    @Autowired
    public LoginController(LoginService loginService, TokenService tokenService, UserService userService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody LoginForm loginForm){
        TokenDTO tokenDTO = loginService.login(loginForm);
        Role role = userService.findOneByLogin(loginForm.getLogin()).getRole();
        Map map = new HashMap();
        map.put("value", tokenDTO.getToken());
        if(role.name().equals(Role.ROLE_ADMIN)) {
            map.put("role", role);
        }
        return ResponseEntity.ok(map);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        String token = authentication.getToken();
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        tokenService.delete(userDetails.getUsername(), token);
    }
}
