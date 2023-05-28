package com.digital_guru.hackathonbackend.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuthRequestDto {
    private String username;
    private String password;
}
