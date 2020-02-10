package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Exceptions.NotFoundException;
import ru.kpfu.itis.Forms.RegistrationForm;
import ru.kpfu.itis.Models.Enums.Role;
import ru.kpfu.itis.Models.Enums.State;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

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
    public void signUp(RegistrationForm registrationForm) {
        String hashPassword = passwordEncoder.encode(registrationForm.getPassword());

        User user = User.builder()
                .firstName(registrationForm.getFirstName())
                .lastName(registrationForm.getLastName())
                .login(registrationForm.getLogin())
                .hashPassword(hashPassword)
                .role(Role.USER)
                .state(State.ACTIVE)
                .email(registrationForm.getEmail())
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

    @Override
    public User findOneByLogin(String login) {
        Optional<User> userCandidate = userRepository.findOneByLogin(login);

        if(userCandidate.isPresent()){
            return userCandidate.get();
        }
        else{
            throw new NotFoundException("User");
        }
    }
}
