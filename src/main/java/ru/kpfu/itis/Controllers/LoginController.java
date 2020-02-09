package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.LoginForm;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Services.LoginService;
import ru.kpfu.itis.Services.TokenService;
import ru.kpfu.itis.Transfer.TokenDTO;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private LoginService loginService;
    private TokenService tokenService;

    @Autowired
    public LoginController(LoginService loginService, TokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginForm loginForm){
        return ResponseEntity.ok(loginService.login(loginForm));
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
