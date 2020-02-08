package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.DateIntervalForm;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Services.DateIntervalService;
import ru.kpfu.itis.Services.UserService;
import ru.kpfu.itis.Transfer.DateIntervalDTO;
import ru.kpfu.itis.Utils.DateIntervalsMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/date-intervals")
public class DateIntervalController {

    private DateIntervalService dateIntervalService;
    private UserService userService;
    private DateIntervalsMapper dateIntervalsMapper;

    @Autowired
    public DateIntervalController(DateIntervalService dateIntervalService, UserService userService, DateIntervalsMapper dateIntervalsMapper) {
        this.dateIntervalService = dateIntervalService;
        this.userService = userService;
        this.dateIntervalsMapper = dateIntervalsMapper;
    }

    @PutMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDateInterval(
             @RequestBody @Valid DateIntervalForm dateIntervalForm){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails userDetails =  (UserDetails)authentication.getDetails();
        dateIntervalService.addDateInterval(userDetails.getUsername(), dateIntervalForm);
    }

    @GetMapping
    public ResponseEntity<List<DateIntervalDTO>> getDateIntervals()
    {
        List<DateInterval> dateIntervalList = dateIntervalService.findAll();
        List<DateIntervalDTO> dtos = dateIntervalList
                .stream().map(dateInterval -> dateIntervalsMapper.toDto(dateInterval))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/my-dates")
//    public ResponseEntity<List<DateIntervalDTO>> getByCurrentUser(){
//        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
//                .getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getDetails();
//        List<DateInterval> dateIntervalList = dateIntervalService.findAllByUser(userDetails.getUsername());
//        List<DateIntervalDTO> dtos = dateIntervalList
//                .stream().map(dateInterval -> dateIntervalsMapper.toDto(dateInterval))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(dtos);
//    }
    public ResponseEntity<List<DateInterval>> get(){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        List<DateInterval> dateIntervalList = dateIntervalService.findAllByUser(userDetails.getUsername());
//        List<DateIntervalDTO> dtos = dateIntervalList
//                .stream().map(dateInterval -> dateIntervalsMapper.toDto(dateInterval))
//                .collect(Collectors.toList());
        return ResponseEntity.ok(dateIntervalList);
    }
}
