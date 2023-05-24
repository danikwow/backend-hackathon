package com.digital_guru.hackathonbackend.mapper;

import com.digital_guru.hackathonbackend.dto.UserDto;
import com.digital_guru.hackathonbackend.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
        UserDto map(User user);

        @InheritInverseConfiguration
        User map(UserDto dto);
}
