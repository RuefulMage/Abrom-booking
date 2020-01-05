package ru.kpfu.itis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.Models.DateInterval;

@Repository
public interface DateIntervalsRepository extends JpaRepository<DateInterval, Long> {

}
