package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Forms.DateIntervalForm;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.Enums.IntervalStatus;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.DateIntervalsRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DateIntervalServiceImpl implements DateIntervalService {

    private DateIntervalsRepository dateIntervalsRepository;
    private UserService userService;
    private CottageService cottageService;

    @Autowired
    public DateIntervalServiceImpl(DateIntervalsRepository dateIntervalsRepository,
                                   UserService userService,
                                   CottageService cottageService) {
        this.dateIntervalsRepository = dateIntervalsRepository;
        this.userService = userService;
        this.cottageService = cottageService;
    }

    @Override
    public DateInterval findByID(Long id) {
        Optional<DateInterval> dateIntervalCandidate =  dateIntervalsRepository.findById(id);

        if(dateIntervalCandidate.isPresent()){
            return dateIntervalCandidate.get();
        }else {
            throw new IllegalArgumentException("Date interval not found");
        }
    }

    @Override
    public List<DateInterval> findAll() {
        return dateIntervalsRepository.findAll();
    }

    @Override
    public void addDateInterval(String userName, DateIntervalForm dateIntervalForm) {
        User user = userService.findOneByLogin(userName);
        Cottage cottage = cottageService.getCottageByID(dateIntervalForm.getCottageID());
        DateInterval dateInterval = DateInterval.builder()
                .startOfInterval(dateIntervalForm.getStartOfInterval())
                .endOfInterval(dateIntervalForm.getEndOfInterval())
                .intervalStatus(IntervalStatus.PENDING)
                .owner(user)
                .cottage(cottage)
                .build();
        if(checkIntervalForFree(dateInterval)){
            dateIntervalsRepository.save(dateInterval);
        }else{
            throw new IllegalArgumentException("Date interval is exists");
        }
    }

    @Override
    public List<DateInterval> findAllByCottage(Cottage cottage) {
        return dateIntervalsRepository.findAllByCottage(cottage);
    }

    @Override
    public void updateStatus(Long id, IntervalStatus status) {
        DateInterval dateInterval = findByID(id);
        dateInterval.setIntervalStatus(status);
        dateIntervalsRepository.save(dateInterval);
    }

    @Override
    public void delete(Long id) {
        findByID(id);
        dateIntervalsRepository.delete(id);
    }


    private boolean checkIntervalForFree(DateInterval dateInterval) {
        List<DateInterval> dateIntervalList = dateIntervalsRepository.findAll();
        for (DateInterval dateIntervalListItem:
             dateIntervalList) {
            Date startDate = dateIntervalListItem.getStartOfInterval();
            Date endDate = dateIntervalListItem.getEndOfInterval();
            if(isWithinRange(dateInterval.getStartOfInterval(),startDate, endDate)
                    || isWithinRange(dateInterval.getEndOfInterval(),startDate, endDate)){
                return false;
            }
        }
        return true;
    }




    private boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        System.out.println(!(testDate.before(startDate) || testDate.after(endDate)));
        return !(testDate.before(startDate) || testDate.after(endDate));
    }


}
