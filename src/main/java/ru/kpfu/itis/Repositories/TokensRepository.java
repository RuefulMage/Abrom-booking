package ru.kpfu.itis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.Models.Token;

import java.util.Optional;

public interface TokensRepository  extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String token);
}
