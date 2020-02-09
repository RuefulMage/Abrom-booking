package ru.kpfu.itis.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.Enums.IntervalStatus;
import ru.kpfu.itis.Services.DateIntervalService;
import ru.kpfu.itis.Transfer.DateIntervalDTO;
import ru.kpfu.itis.Utils.DateIntervalsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private DateIntervalService dateIntervalService;
    private DateIntervalsMapper dateIntervalsMapper;

    @Autowired
    public AdminController(DateIntervalService dateIntervalService, DateIntervalsMapper dateIntervalsMapper) {
        this.dateIntervalService = dateIntervalService;
        this.dateIntervalsMapper = dateIntervalsMapper;
    }

    @GetMapping("/requests")
    public ResponseEntity<List<DateIntervalDTO>> getAllRequests(){
        List<DateInterval> dateIntervalList = dateIntervalService.findAllByStatus(IntervalStatus.PENDING);
        List<DateIntervalDTO> dtos = dateIntervalList
                .stream().map(dateInterval -> dateIntervalsMapper.toDto(dateInterval))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
