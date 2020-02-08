package ru.kpfu.itis.Utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Transfer.DateIntervalDTO;


@Component
public class DateIntervalsMapper implements DTOMapper<DateInterval, DateIntervalDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public DateIntervalsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DateInterval toEntity(DateIntervalDTO dto) {
        DateInterval dateInterval = modelMapper.map(dto, DateInterval.class);
        return dateInterval;
    }

    @Override
    public DateIntervalDTO toDto(DateInterval entity) {
        DateIntervalDTO dateIntervalDTO = modelMapper.map(entity, DateIntervalDTO.class);
        return dateIntervalDTO;
    }
}
