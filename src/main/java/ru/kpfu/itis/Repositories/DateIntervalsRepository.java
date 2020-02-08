package ru.kpfu.itis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.DateInterval;

import java.util.List;
import java.util.Optional;

@Repository
public interface DateIntervalsRepository extends JpaRepository<DateInterval, Long> {

    List<DateInterval> findAllByCottage(Cottage cottage);

    Optional<DateInterval> findById(Long id);

}
