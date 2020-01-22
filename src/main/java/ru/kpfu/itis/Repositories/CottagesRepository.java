package ru.kpfu.itis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.Models.Cottage;

public interface CottagesRepository extends JpaRepository<Cottage, Long> {
}
