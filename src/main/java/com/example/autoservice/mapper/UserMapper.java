package com.example.autoservice.mapper;

import com.example.autoservice.dto.UserDTO;
import com.example.autoservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO dto);
}
