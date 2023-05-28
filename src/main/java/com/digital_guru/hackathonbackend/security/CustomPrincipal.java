package com.digital_guru.hackathonbackend.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomPrincipal implements Principal {
    private Long id;
    private String name;
}
