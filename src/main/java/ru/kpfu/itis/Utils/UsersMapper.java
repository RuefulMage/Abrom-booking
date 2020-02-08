package ru.kpfu.itis.Utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.itis.Models.User;
import ru.kpfu.itis.Transfer.UserDTO;

public class UsersMapper implements DTOMapper<User, UserDTO> {
    private ModelMapper modelMapper;

    @Autowired
    public UsersMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User toEntity(UserDTO dto) {
        User user = modelMapper.map(dto,User.class);
        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        UserDTO userDTO = modelMapper.map(entity, UserDTO.class);
        return userDTO;
    }
}
