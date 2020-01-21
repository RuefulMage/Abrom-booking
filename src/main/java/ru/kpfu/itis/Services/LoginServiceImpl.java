package ru.kpfu.itis.Services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Forms.LoginForm;
import ru.kpfu.itis.Models.Token;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.TokensRepository;
import ru.kpfu.itis.Repositories.UserRepository;
import ru.kpfu.itis.Transfer.TokenDTO;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TokensRepository tokensRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TokenDTO login(LoginForm loginForm) {
        Optional<User> userCandidate = userRepository.findOneByLogin(loginForm.getLogin());

        if (userCandidate.isPresent()){
            User user = userCandidate.get();

            if(passwordEncoder.matches(loginForm.getPassword(), user.getHashPassword())){
                Token token = Token.builder()
                        .user(user)
                        .value(RandomStringUtils.random(10, true, true))
                        .build();

                tokensRepository.save(token);
//                System.out.println(TokenDTO.from(token));
                return TokenDTO.from(token);
            }
        }throw new IllegalArgumentException("User not found");
    }
}
