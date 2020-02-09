package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Models.DateInterval;
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
    public JavaMailSender emailSender;

    public DateIntervalController(DateIntervalService dateIntervalService,
                                  UserService userService, DateIntervalsMapper dateIntervalsMapper,
                                  JavaMailSender emailSender) {
        this.dateIntervalService = dateIntervalService;
        this.userService = userService;
        this.dateIntervalsMapper = dateIntervalsMapper;
        this.emailSender = emailSender;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDateInterval(
             @RequestBody @Valid DateIntervalDTO dateIntervalDTO){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getDetails();
        dateIntervalService.addDateInterval(userDetails.getUsername(), dateIntervalDTO);

        String email = userService.findOneByLogin(userDetails.getUsername()).getEmail();
        sendMail(email);
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
    public ResponseEntity<List<DateIntervalDTO>> getByCurrentUser(){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        List<DateInterval> dateIntervalList = dateIntervalService.findAllByUser(userDetails.getUsername());
        List<DateIntervalDTO> dtos = dateIntervalList
                .stream().map(dateInterval -> dateIntervalsMapper.toDto(dateInterval))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //TODO сделать рассылку админам
    public void sendMail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("New request");
        message.setText("Hello, you have a new rental request from");
        this.emailSender.send(message);

    }
}
