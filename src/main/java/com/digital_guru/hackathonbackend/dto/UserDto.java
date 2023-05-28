package com.digital_guru.hackathonbackend.dto;

import com.digital_guru.hackathonbackend.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private UserRole userRole;
    private String firstName;
    private String secondName;
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Date createTime;
}
