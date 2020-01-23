package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Forms.CottageForm;
import ru.kpfu.itis.Services.CottageService;

@RestController
public class CottageController {

    @Autowired
    private CottageService cottageService;

    //TODO сделать общий метод для изменения
    @PostMapping("/cottage/{id}/description")
    public ResponseEntity updateDescription(@PathVariable("id") Long id, CottageForm cottageForm){
        cottageService.updateDescription(cottageForm.getDescription(), id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cottage/{id}/address")
    public ResponseEntity updateAddress(@PathVariable("id") Long id, CottageForm cottageForm){
        cottageService.updateAddress(cottageForm.getAddress(), id);
        return ResponseEntity.ok().build();
    }
}
