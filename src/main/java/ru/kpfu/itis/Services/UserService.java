package ru.kpfu.itis.Services;

import ru.kpfu.itis.Forms.UserForm;
import ru.kpfu.itis.Models.User;

import java.util.List;


public interface UserService {
    void signUp(UserForm userForm);

    List<User> findAll();

    User findOne(Long user_id);

    User findOneByLogin(String login);
}
