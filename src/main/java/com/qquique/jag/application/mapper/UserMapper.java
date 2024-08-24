package com.qquique.jag.application.mapper;

import com.qquique.jag.application.dto.UserDTO;
import com.qquique.jag.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO mapToDTO(User user);

    User mapToDomain(UserDTO userDTO);

    void updateUserFromDTo(UserDTO userDto, @MappingTarget User user);
}
