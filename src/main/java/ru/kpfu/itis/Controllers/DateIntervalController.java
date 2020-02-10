package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Services.DateIntervalService;
import ru.kpfu.itis.Services.UserService;
import ru.kpfu.itis.Transfer.DateIntervalDTO;
import ru.kpfu.itis.Utils.DateIntervalsMapper;
import ru.kpfu.itis.Utils.MailSender;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/date-intervals")
@PropertySource("classpath:application.properties")
public class DateIntervalController {

    @Resource
    private Environment env;

    private DateIntervalService dateIntervalService;
    private UserService userService;
    private DateIntervalsMapper dateIntervalsMapper;
    private MailSender mailSender;

    @Autowired
    public DateIntervalController(DateIntervalService dateIntervalService,
                                  UserService userService,
                                  DateIntervalsMapper dateIntervalsMapper,
                                  MailSender mailSender) {
        this.dateIntervalService = dateIntervalService;
        this.userService = userService;
        this.dateIntervalsMapper = dateIntervalsMapper;
        this.mailSender = mailSender;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDateInterval(
             @RequestBody @Valid DateIntervalDTO dateIntervalDTO){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getDetails();
        dateIntervalService.addDateInterval(userDetails.getUsername(), dateIntervalDTO);

        String[] emails = new String[3];
        emails[0] = env.getRequiredProperty("admin-mail1");
        emails[1] = env.getRequiredProperty("admin-mail2");
        emails[2] = env.getRequiredProperty("admin-mail3");
        mailSender.sendMail(emails, "Hello, you have a new rental request", "New request");
    }

    @GetMapping("/all")
    public ResponseEntity<List<DateIntervalDTO>> getDateIntervals()
    {
        List<DateInterval> dateIntervalList = dateIntervalService.findAll();
        List<DateIntervalDTO> dtos = dateIntervalList
                .stream().map(dateInterval -> dateIntervalsMapper.toDto(dateInterval))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<DateIntervalDTO>> getDateIntervalsExcludeDeleted()
    {
        List<DateInterval> dateIntervalList = dateIntervalService.findAllExcludeDeleted();
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

}
