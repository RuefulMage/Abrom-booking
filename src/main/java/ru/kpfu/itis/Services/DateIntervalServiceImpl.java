package ru.kpfu.itis.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Exceptions.NotFoundException;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Models.Enums.IntervalStatus;
import ru.kpfu.itis.Models.Enums.Role;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Repositories.DateIntervalsRepository;
import ru.kpfu.itis.Transfer.DateIntervalDTO;
import ru.kpfu.itis.Utils.MailSender;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

@Service
@Slf4j
public class DateIntervalServiceImpl implements DateIntervalService {

    private DateIntervalsRepository dateIntervalsRepository;
    private UserService userService;
    private CottageService cottageService;
    private MailSender mailSender;

    @Autowired
    public DateIntervalServiceImpl(DateIntervalsRepository dateIntervalsRepository,
                                   UserService userService,
                                   CottageService cottageService,
                                   MailSender mailSender) {
        this.dateIntervalsRepository = dateIntervalsRepository;
        this.userService = userService;
        this.cottageService = cottageService;
        this.mailSender = mailSender;
    }

    @Override
    public DateInterval findByID(Long id) {
        Optional<DateInterval> dateIntervalCandidate =  dateIntervalsRepository.findById(id);

        if(dateIntervalCandidate.isPresent()){
            return dateIntervalCandidate.get();
        }else {
            throw new NotFoundException("Date");
        }
    }

    @Override
    public List<DateInterval> findAll() {
        List<DateInterval> dateIntervalList = dateIntervalsRepository.findAll();
        checkForOld(dateIntervalList);
        if (dateIntervalList.isEmpty()){
            throw new NotFoundException("Date intervals");
        }
        return dateIntervalList;
    }

    @Override
    public void addDateInterval(String userName, DateIntervalDTO dateIntervalDTO) {
        User user = userService.findOneByLogin(userName);
        Cottage cottage = cottageService.getCottageByID(dateIntervalDTO.getCottageID());
        DateInterval dateInterval = DateInterval.builder()
                .startOfInterval(dateIntervalDTO.getStartOfInterval())
                .endOfInterval(dateIntervalDTO.getEndOfInterval())
                .owner(user)
                .cottage(cottage)
                .build();
        if(user.getRole().equals(Role.ROLE_ADMIN)){
            dateInterval.setIntervalStatus(IntervalStatus.BOOKED);
        }else {
            dateInterval.setIntervalStatus(IntervalStatus.PENDING);
        }
        if(checkIntervalForFree(dateInterval)){
            dateIntervalsRepository.save(dateInterval);
        }else{
            throw new IllegalArgumentException("Date interval is exists");
        }
    }

    @Override
    public List<DateInterval> findAllByCottage(Cottage cottage) {
        List<DateInterval> dateIntervalList = dateIntervalsRepository.findAllByCottage(cottage);
        if(dateIntervalList.isEmpty()){
            throw new NotFoundException("Date intervals");
        }
        return dateIntervalList;
    }

    @Override
    public void updateStatus(Long id, IntervalStatus status) {
        DateInterval dateInterval = findByID(id);
        dateInterval.setIntervalStatus(status);
        dateIntervalsRepository.save(dateInterval);
        String emailTo = dateInterval.getOwner().getEmail();
        mailSender.sendMail(emailTo, "Your date interval have been changed a status", "Notification");
    }

    @Override
    public void delete(Long id) {
        findByID(id);
        dateIntervalsRepository.delete(id);
    }

    @Override
    public List<DateInterval> findAllByUser(String userName) {
        User user = userService.findOneByLogin(userName);
        List<DateInterval> dateIntervalList = dateIntervalsRepository.findAllByOwner(user);
        if(dateIntervalList.isEmpty()){
            throw new NotFoundException("Date intervals");
        }
        return dateIntervalList;
    }

    @Override
    public List<DateInterval> findAllByStatus(IntervalStatus status) {
        List<DateInterval> dateIntervalList = dateIntervalsRepository.findAllByIntervalStatus(status);
        log.info("Dates", dateIntervalList);
        if(dateIntervalList.isEmpty()){
            throw new NotFoundException("Date");
        }
        return dateIntervalList;
    }

    @Override
    public List<DateInterval> findAllExcludeDeleted() {
        List<DateInterval> dateIntervalList = findAll();
        List<DateInterval> list = dateIntervalList.stream()
                .filter(dateInterval -> !(dateInterval.getIntervalStatus().equals(IntervalStatus.DELETED)))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            throw new NotFoundException("Date");
        }
        return list;
    }

    @Override
    public List<DateInterval> findAllExcludeDeletedByCottage(Long id) {
        List<DateInterval> dateIntervalList = dateIntervalsRepository.findAllByCottage_Id(id);
        List<DateInterval> list = dateIntervalList.stream()
                .filter(dateInterval -> !(dateInterval.getIntervalStatus().equals(IntervalStatus.DELETED)))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            throw new NotFoundException("Date");
        }
        return list;
    }

    @Override
    public List<DateInterval> findAllByUserAndCottage(String username, Long id) {
        User user = userService.findOneByLogin(username);
        List<DateInterval> dateIntervalList = dateIntervalsRepository.findAllByOwnerAndCottage_Id(user, id);
        List<DateInterval> list = dateIntervalList.stream()
                .filter(dateInterval -> !(dateInterval.getIntervalStatus().equals(IntervalStatus.DELETED)))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            throw new NotFoundException("Date");
        }
        return list;
    }


    private boolean checkIntervalForFree(DateInterval dateInterval) {
        List<DateInterval> dateIntervalList = findAllExcludeDeleted();
        for (DateInterval dateIntervalListItem:
             dateIntervalList) {
            Date startDate = dateIntervalListItem.getStartOfInterval();
            Date endDate = dateIntervalListItem.getEndOfInterval();
            if(!dateInterval.getCottage().equals(dateIntervalListItem.getCottage())){
                continue;
            }
            if(isWithinRange(dateInterval.getStartOfInterval(),startDate, endDate)
                    || isWithinRange(dateInterval.getEndOfInterval(),startDate, endDate)
                    || isWithinRange(startDate, dateInterval.getStartOfInterval(), dateInterval.getEndOfInterval())
                    || isWithinRange(endDate, dateInterval.getStartOfInterval(), dateInterval.getEndOfInterval())){
                return false;
            }
        }
        return true;
    }


    private boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        System.out.println(!(testDate.before(startDate) || testDate.after(endDate)));
        return !(testDate.before(startDate) || testDate.after(endDate));
    }

    public void checkForOld(List<DateInterval> dateIntervalList){
        Date today = new Date();
        log.info(today.toString());
        for (DateInterval interval: dateIntervalList) {
            long msTimeDistance = today.getTime() - interval.getEndOfInterval().getTime();
            long msDay = 24 * 60 * 60 * 1000;  //сколько миллисекунд в одних сутках
            int dayCount = (int) (msTimeDistance/msDay);
            if (dayCount > 14){
                delete(interval.getId());
            }
        }
    }


}
