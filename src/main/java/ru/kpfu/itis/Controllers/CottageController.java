package ru.kpfu.itis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Forms.CottageForm;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Services.CottageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cottages")
public class CottageController {

    private CottageService cottageService;

    @Autowired
    public CottageController(CottageService cottageService) {
        this.cottageService = cottageService;
    }

    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, CottageForm cottageForm){
        cottageService.update(cottageForm, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cottage> getByID(@PathVariable("id") Long id){
        return ResponseEntity.ok(cottageService.getCottageByID(id));
    }
}
