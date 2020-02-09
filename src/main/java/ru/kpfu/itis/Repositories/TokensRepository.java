package ru.kpfu.itis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.Models.Token;

import java.util.Optional;

@Repository
public interface TokensRepository  extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String token);
    Optional<Token> findByUser_IdAndAndValue(Long id, String value);
}
