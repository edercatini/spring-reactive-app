package com.ec.userservice.model.mapper;

import com.ec.userservice.model.dto.UserDto;
import com.ec.userservice.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);
    UserDto toDto(User user);
}