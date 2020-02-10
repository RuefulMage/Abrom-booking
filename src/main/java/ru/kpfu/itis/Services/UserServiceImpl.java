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
        Optional<User> userCandidateLogin = userRepository.findOneByLogin(registrationForm.getLogin());
        Optional<User> userCandidateEmail = userRepository.findByEmail(registrationForm.getEmail());
        if(userCandidateEmail.isPresent() || userCandidateLogin.isPresent()){
            throw new IllegalArgumentException("User is exists");
        }
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
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            throw new NotFoundException("User");
        }
        return userList;
    }

    @Override
    public User findOne(Long user_id) {
        Optional<User> userCandidates = userRepository.findById(user_id);
        if (userCandidates.isPresent()){
            return userCandidates.get();
        }else{
            throw new NotFoundException("User");
        }
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
