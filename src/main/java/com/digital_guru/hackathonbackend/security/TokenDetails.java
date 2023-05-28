package com.digital_guru.hackathonbackend.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetails {

    private Long userId;
    private String token;
    private Date issuedAt;
    private Date expiresAt;
}
