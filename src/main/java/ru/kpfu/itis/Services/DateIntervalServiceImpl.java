package ru.kpfu.itis.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Forms.DateIntervalForm;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.Enums.IntervalStatus;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.CottagesRepository;
import ru.kpfu.itis.Repositories.DateIntervalsRepository;
import ru.kpfu.itis.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DateIntervalServiceImpl implements DateIntervalService {
    @Autowired
    private DateIntervalsRepository dateIntervalsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CottageService cottageService;


    @Override
    public List<DateInterval> findAll() {
        return dateIntervalsRepository.findAll();
    }

    @Override
    public void addDateInterval(String userName, DateIntervalForm dateIntervalForm) {


        User user = userService.findOneByLogin(userName);

        Cottage cottage = cottageService.getCottafeByID(dateIntervalForm.getCottageID());

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
    public boolean checkIntervalForFree(DateInterval dateInterval) {
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

    @Override
    public List<DateInterval> findAllByCottage(Cottage cottage) {
        return null;
    }

    boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        System.out.println(!(testDate.before(startDate) || testDate.after(endDate)));
        return !(testDate.before(startDate) || testDate.after(endDate));
    }


}
