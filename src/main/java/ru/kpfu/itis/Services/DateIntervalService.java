package ru.kpfu.itis.Services;

import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.Enums.IntervalStatus;
import ru.kpfu.itis.Transfer.DateIntervalDTO;

import java.util.List;

public interface DateIntervalService {
    DateInterval findByID(Long id);

    List<DateInterval> findAll();

    void addDateInterval(String userName, DateIntervalDTO dateIntervalDTO);

    List<DateInterval> findAllByCottage(Cottage cottage);

    void updateStatus(Long id, IntervalStatus status);

    void delete(Long id);

    List<DateInterval> findAllByUser(String userName);

    List<DateInterval> findAllByStatus(IntervalStatus status);

    List<DateInterval> findAllExcludeDeleted();
}
