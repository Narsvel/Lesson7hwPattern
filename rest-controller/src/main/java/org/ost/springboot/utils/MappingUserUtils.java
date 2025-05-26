package org.ost.springboot.utils;

import org.modelmapper.ModelMapper;
import org.ost.springboot.dto.UserDTO;
import org.ost.springboot.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingUserUtils {

    private final ModelMapper modelMapper;

    public MappingUserUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //можно использовать ModelMapper т.к. не придется мапить каждое поле отдельно (их может быть очень много)
    public UserDTO mapToDto(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public User mapToUser(UserDTO dto){
        return modelMapper.map(dto, User.class);
    }

    public List<UserDTO> mapToDtoList(List<User> users) {
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

}
