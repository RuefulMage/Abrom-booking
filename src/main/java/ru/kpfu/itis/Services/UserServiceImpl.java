package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Forms.UserForm;
import ru.kpfu.itis.Models.Role;
import ru.kpfu.itis.Models.State;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());

        User user = User.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .login(userForm.getLogin())
                .hashPassword(hashPassword)
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();


        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
            return userRepository.findAll();
    }

    @Override
    public User findOne(Long user_id) {
        return userRepository.findOne(user_id);
    }
}
