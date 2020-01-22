package ru.kpfu.itis.Services;

import ru.kpfu.itis.Forms.DateIntervalForm;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.User;

import java.util.List;

public interface DateIntervalService {
    List<DateInterval> findAll();

    void addDateInterval(User user, DateIntervalForm dateIntervalForm);
}
