package com.digital_guru.hackathonbackend.mapper;

import com.digital_guru.hackathonbackend.dto.UserDto;
import com.digital_guru.hackathonbackend.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

public class UserMapperImpl implements UserMapper{
    @Override
    public UserDto map(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setUserRole(entity.getUserRole());
        return dto;
    }

    @Override
    public User map(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setUserRole(dto.getUserRole());
        entity.setUpdateTime(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setEnabled(true);
        return entity;
    }
}
