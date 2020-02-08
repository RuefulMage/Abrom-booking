package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Forms.CottageForm;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.CottagesRepository;

import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService {

    private CottagesRepository cottagesRepository;
    private UserService userService;

    @Autowired
    public CottageServiceImpl(CottagesRepository cottagesRepository, UserService userService) {
        this.cottagesRepository = cottagesRepository;
        this.userService = userService;
    }



    @Override
    public void update(CottageForm cottageForm, Long id) {
        Optional<Cottage> cottageCandidate = cottagesRepository.findById(id);
        if(cottageCandidate.isPresent()){
            Cottage cottage = cottageCandidate.get();
            Cottage.builder()
                    .description(cottageForm.getDescription())
                    .address(cottageForm.getAddress())
                    .build();
            cottagesRepository.save(cottage);
        }else{
            throw new IllegalArgumentException("Cottage is not found");
        }
    }

    @Override
    public void addCottage(String userName, CottageForm cottageForm) {
        User user = userService.findOneByLogin(userName);
        Cottage cottage = Cottage.builder()
                .address(cottageForm.getAddress())
                .description(cottageForm.getDescription())
                .build();
        cottagesRepository.save(cottage);
    }

    @Override
    public Cottage getCottageByID(Long id) {
        Optional<Cottage> cottageCandidate = cottagesRepository.findById(id);

        if(cottageCandidate.isPresent()){
            return cottageCandidate.get();
        }
        else{
            throw new IllegalArgumentException("Cottage not found");
        }
    }
}
