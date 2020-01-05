package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Services.DateIntervalService;

import java.util.List;

@RestController
public class DateIntervalController {
    @Autowired
    private DateIntervalService dateIntervalService;

    @GetMapping("/dates")
    public List<DateInterval> getDateIntervals(){
        return dateIntervalService.findAll();
    }
}
