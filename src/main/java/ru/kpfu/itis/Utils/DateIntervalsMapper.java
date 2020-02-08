package ru.kpfu.itis.Utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.Models.DateInterval;
import ru.kpfu.itis.Transfer.DateIntervalDTO;
import ru.kpfu.itis.Transfer.UserDTO;


@Component
public class DateIntervalsMapper implements DTOMapper<DateInterval, DateIntervalDTO> {

    private final ModelMapper modelMapper;
    private final UsersMapper usersMapper;


    public DateIntervalsMapper(ModelMapper modelMapper, UsersMapper usersMapper) {
        this.modelMapper = modelMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    public DateInterval toEntity(DateIntervalDTO dto) {
        DateInterval dateInterval = modelMapper.map(dto, DateInterval.class);
        return dateInterval;
    }

    @Override
    public DateIntervalDTO toDto(DateInterval entity) {
        UserDTO userDTO = usersMapper.toDto(entity.getOwner());
        DateIntervalDTO dateIntervalDTO = modelMapper.map(entity, DateIntervalDTO.class);
        dateIntervalDTO.setCottageID(entity.getCottage().getId());
        dateIntervalDTO.setOwner(userDTO);
        return dateIntervalDTO;
    }
}
