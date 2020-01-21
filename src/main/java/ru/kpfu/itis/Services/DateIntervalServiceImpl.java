package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Forms.DateIntervalForm;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.DateIntervalsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DateIntervalServiceImpl implements DateIntervalService {
    @Autowired
    private DateIntervalsRepository dateIntervalsRepository;

    @Override
    public List<DateInterval> findAll() {
        return dateIntervalsRepository.findAll();
    }

    @Override
    public String addDateInterval(User user, DateIntervalForm dateIntervalForm) {
        return user.toString();
    }
}
