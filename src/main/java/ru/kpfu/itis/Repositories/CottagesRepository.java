package ru.kpfu.itis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.Models.Cottage;

import java.util.Optional;

@Repository
public interface CottagesRepository extends JpaRepository<Cottage, Long> {

    Optional<Cottage> findById(Long id);

}
