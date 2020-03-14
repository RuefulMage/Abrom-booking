package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Security.Token.TokenAuthentication;
import ru.kpfu.itis.Transfer.CottageDTO;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Services.CottageService;
import ru.kpfu.itis.Utils.MailSender;
import ru.kpfu.itis.Utils.Mappers.CottageMapper;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cottages")
@PropertySource("classpath:application.properties")
public class CottageController {

    @Resource
    private Environment env;

    private CottageService cottageService;
    private CottageMapper cottageMapper;
    private MailSender mailSender;

    @Autowired
    public CottageController(CottageService cottageService,
                             CottageMapper cottageMapper,
                             MailSender mailSender) {
        this.cottageService = cottageService;
        this.cottageMapper = cottageMapper;
        this.mailSender = mailSender;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDateInterval(
            @RequestBody @Valid CottageDTO cottageDTO){
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getDetails();
        cottageService.addCottage(userDetails.getUsername(), cottageDTO);

        String[] emails = new String[3];
        emails[0] = env.getRequiredProperty("admin-mail1");
        emails[1] = env.getRequiredProperty("admin-mail2");
        emails[2] = env.getRequiredProperty("admin-mail3");
        mailSender.sendMail(emails, "Hello, someone added a new cottage. Go to rent-abrom.ru to know more",
                "New cottage");
    }

    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, CottageDTO cottageDTO){
        cottageService.update(cottageDTO, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CottageDTO> getByID(@PathVariable("id") Long id){
        return ResponseEntity.ok(cottageMapper.toDto(cottageService.getCottageByID(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CottageDTO>> getAll(){
        List<Cottage> cottageList = cottageService.getAllCottages();
        List<CottageDTO> cottageDTOList = cottageList.stream()
                .map(cottage -> cottageMapper.toDto(cottage)).collect(Collectors.toList());
        return ResponseEntity.ok(cottageDTOList);
    }
}
