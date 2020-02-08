package ru.kpfu.itis.Services;

import ru.kpfu.itis.Forms.DateIntervalForm;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.Enums.IntervalStatus;

import java.util.List;

public interface DateIntervalService {
    DateInterval findByID(Long id);

    List<DateInterval> findAll();

    void addDateInterval(String userName, DateIntervalForm dateIntervalForm);

    List<DateInterval> findAllByCottage(Cottage cottage);

    void updateStatus(Long id, IntervalStatus status);

    void delete(Long id);
}
