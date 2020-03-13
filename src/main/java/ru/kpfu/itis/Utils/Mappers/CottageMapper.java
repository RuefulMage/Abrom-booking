package ru.kpfu.itis.Utils.Mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.Models.Cottage;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Transfer.CottageDTO;
import ru.kpfu.itis.Transfer.DateIntervalDTO;
import ru.kpfu.itis.Transfer.UserDTO;
import ru.kpfu.itis.Utils.DTOMapper;
import ru.kpfu.itis.Utils.UsersMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class CottageMapper implements DTOMapper<Cottage, CottageDTO> {
    private final ModelMapper modelMapper;
    private final DateIntervalsMapper dateIntervalsMapper;
    private final UsersMapper usersMapper;

    @Autowired
    public CottageMapper(ModelMapper modelMapper,
                         DateIntervalsMapper dateIntervalsMapper,
                         UsersMapper usersMapper) {
        this.modelMapper = modelMapper;
        this.dateIntervalsMapper = dateIntervalsMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    public Cottage toEntity(CottageDTO dto) {
        Cottage cottage = modelMapper.map(dto, Cottage.class);
        return cottage;
    }

    @Override
    public CottageDTO toDto(Cottage entity){
        CottageDTO cottageDTO = modelMapper.map(entity, CottageDTO.class);
        List<DateIntervalDTO> intervalDTOS = new ArrayList<>();
        entity.getDateIntervals().stream()
                .forEach(dateInterval -> {
                    intervalDTOS.add(dateIntervalsMapper.toDto(dateInterval));
                });
        cottageDTO.setDateIntervalDTOList(intervalDTOS);
        return cottageDTO;
    }
}
