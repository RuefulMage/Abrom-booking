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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/date-intervals")
public class DateIntervalController {

    private DateIntervalService dateIntervalService;
    private UserService userService;

    @Autowired
    public DateIntervalController(DateIntervalService dateIntervalService, UserService userService) {
        this.dateIntervalService = dateIntervalService;
        this.userService = userService;
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
    public ResponseEntity<List<DateInterval>> getDateIntervals()
    {
        return ResponseEntity.ok(dateIntervalService.findAll());
    }

    @GetMapping("/my-dates")
    public ResponseEntity<List<DateInterval>> getByCurrentUser(){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        return ResponseEntity.ok(dateIntervalService.findAllByUser(userDetails.getUsername()));
    }
}
