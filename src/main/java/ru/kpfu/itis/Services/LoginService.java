package ru.kpfu.itis.Services;

import ru.kpfu.itis.Forms.LoginForm;
import ru.kpfu.itis.Transfer.TokenDTO;

public interface LoginService {
    TokenDTO login(LoginForm loginForm);
}
