package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Repositories.CottagesRepository;

import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService {

    @Autowired
    private CottagesRepository cottagesRepository;


    @Override
    public void updateDescription(String description, Long id) {
        Optional<Cottage> cottageCandidate = cottagesRepository.findById(id);
        if( cottageCandidate.isPresent()){
            Cottage cottage = cottageCandidate.get();
            cottage.setDescription(description);
            cottagesRepository.save(cottage);
        }else{
            throw new IllegalArgumentException("Cottage is not exits");
        }

    }

    @Override
    public void updateAddress(String address, Long id) {
        Optional<Cottage> cottageCandidate = cottagesRepository.findById(id);
        if( cottageCandidate.isPresent()){
            Cottage cottage = cottageCandidate.get();
            cottage.setAddress(address);
            cottagesRepository.save(cottage);
        }else{
            throw new IllegalArgumentException("Cottage is not exits");
        }

    }


    //TODO реализовать addCottage
    @Override
    public void addCottage(Cottage cottage) {

    }

    @Override
    public Cottage getCottafeByID(Long id) {
        Optional<Cottage> cottageCandidate = cottagesRepository.findById(id);

        if(cottageCandidate.isPresent()){
            return cottageCandidate.get();
        }
        else{
            throw new IllegalArgumentException("Cottage not found");
        }
    }
}
