package ru.kpfu.itis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.Models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);
}
