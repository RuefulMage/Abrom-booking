package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Exceptions.NotFoundException;
import ru.kpfu.itis.Transfer.CottageDTO;
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
    public void update(CottageDTO cottageDTO, Long id) {
        Optional<Cottage> cottageCandidate = cottagesRepository.findById(id);
        if(cottageCandidate.isPresent()){
            Cottage cottage = cottageCandidate.get();
            Cottage.builder()
                    .description(cottageDTO.getDescription())
                    .address(cottageDTO.getAddress())
                    .build();
            cottagesRepository.save(cottage);
        }else{
            throw new NotFoundException("Cottage");
        }
    }

    @Override
    public void addCottage(String userName, CottageDTO cottageDTO) {
        User user = userService.findOneByLogin(userName);
        Cottage cottage = Cottage.builder()
                .address(cottageDTO.getAddress())
                .description(cottageDTO.getDescription())
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
            throw new NotFoundException("Cottage");
        }
    }
}
