package com.compasso.demo_park_api.web.dto.mapper;

import com.compasso.demo_park_api.entity.User;
import com.compasso.demo_park_api.web.dto.UserCreateDTO;
import com.compasso.demo_park_api.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserCreateDTO createDTO){
        return new ModelMapper().map(createDTO, User.class);
    }

    public static UserResponseDto toDto(User user){
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User,UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(props);
        return modelMapper.map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<User> users){
        return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
