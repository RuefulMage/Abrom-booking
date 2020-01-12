package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.DateIntervalForm;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Services.DateIntervalService;
import ru.kpfu.itis.Services.UserService;

import java.util.List;

@RestController
public class DateIntervalController {
    @Autowired
    private DateIntervalService dateIntervalService;

    @Autowired
    private UserService userService;

    @GetMapping("/dates")
    public List<DateInterval> getDateIntervals(){
        return dateIntervalService.findAll();
    }

    @PutMapping("/date/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addDateInterval(
            TokenAuthentication authentication,
            @RequestBody DateIntervalForm dateIntervalForm){
        if(authentication == null){
            return "SORRYYYYYYYYY";
        }
        UserDetails userDetails =  (UserDetails)authentication.getDetails();
        User user = userService.findOneByLogin(userDetails.getUsername());
        return dateIntervalService.addDateInterval(user, dateIntervalForm);
    }
}
