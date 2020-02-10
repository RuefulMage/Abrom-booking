package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Exceptions.NotFoundException;
import ru.kpfu.itis.Models.Token;
import ru.kpfu.itis.Repositories.TokensRepository;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    private TokensRepository tokensRepository;
    private UserService userService;

    @Autowired
    public TokenServiceImpl(TokensRepository tokensRepository, UserService userService) {
        this.tokensRepository = tokensRepository;
        this.userService = userService;
    }

    @Override
    public void delete(String userName, String value) {
        Long userID = userService.findOneByLogin(userName).getId();
        Optional<Token> tokenCandidate = tokensRepository.findByUser_IdAndAndValue(userID, value);
        if(tokenCandidate.isPresent()){
            tokensRepository.delete(tokenCandidate.get().getId());
        }else {
            throw new NotFoundException("Token");
        }
    }
}