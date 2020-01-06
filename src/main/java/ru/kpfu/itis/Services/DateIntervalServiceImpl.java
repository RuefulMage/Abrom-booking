package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Repositories.DateIntervalsRepository;

import java.util.List;

@Service
public class DateIntervalServiceImpl implements DateIntervalService {
    @Autowired
    private DateIntervalsRepository dateIntervalsRepository;

    @Override
    public List<DateInterval> findAll() {
        return dateIntervalsRepository.findAll();
    }
}