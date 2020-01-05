package ru.kpfu.itis.Security.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.UserRepository;
import ru.kpfu.itis.Services.UserService;

import java.util.Optional;

@Service
public class UserDerailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository  userRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userCandidate = userRepository.findOneByLogin(login);
        if (userCandidate.isPresent()) {
            return new UserDetailsImpl(userCandidate.get());
        } else throw new IllegalArgumentException("User not found");
    }
}